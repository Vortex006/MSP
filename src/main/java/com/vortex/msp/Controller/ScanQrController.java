package com.vortex.msp.Controller;

import com.vortex.msp.Service.RedisService;
import com.vortex.msp.Utils.DataUtil;
import com.vortex.msp.Utils.Result;
import com.vortex.msp.Utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/scanQr")
public class ScanQrController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RedisService redisService;
    private final String Prefix = "ScanLogin-QrId-";

    @Autowired
    public ScanQrController(RedisService redisService) {
        this.redisService = redisService;
    }

    ;

    @GetMapping
    public Result CreateQrCode() {
        String qrId = DataUtil.getUUID();
        logger.info("二维码id：{}", qrId);
        String base64 = DataUtil.getQrCode(qrId);
        Map<String, String> map = new HashMap<>();
        map.put("qrId", qrId);
        map.put("base64", base64);
        boolean isSave = redisService.setString(Prefix + qrId, String.valueOf(ScanStatus.UnScan), 5, TimeUnit.MINUTES);
        if (!isSave) {
            logger.error("Redis存储二维码失败，失败的二维码ID==>{}", qrId);
            return Result.FAILED("二维码生成失败");
        }
        return Result.SUCCEED(map);
    }

    @GetMapping("/status/{qrId}")
    public Result GetQrCodeStatus(@PathVariable("qrId") String qrId) {
        String status = redisService.getString(Prefix + qrId).toString();
        return Result.SUCCEED(status);
    }

    @PostMapping("/scaned")
    public Result ScanQrCode(@RequestBody Map<String, String> body) {
        // 1.先判断二维码是否已经被扫描
        String qrId = body.get("qrId");
        String status = String.valueOf(redisService.getString(Prefix + qrId));
        if (!status.equals(ScanStatus.UnScan.toString())) {
            return Result.FAILED("二维码已过期");
        }
        boolean isSave = redisService.setString(Prefix + qrId, String.valueOf(ScanStatus.Scaned), 30, TimeUnit.SECONDS);
        if (isSave) {
            return Result.SUCCEED();
        } else {
            return Result.FAILED("二维码已过期");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestAttribute("userId") String userId, @RequestBody Map<String, String> body) {
        String qrId = body.get("qrId");
        String status = String.valueOf(redisService.getString(Prefix + qrId));
        if (!status.equals(ScanStatus.Scaned.toString())) {
            return Result.FAILED("二维码已过期");
        }
        boolean isSave = redisService.setString(Prefix + qrId, String.valueOf(ScanStatus.LoginSuccess), 10,
                TimeUnit.SECONDS);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        String token = TokenUtil.getToken(map);
        boolean isSave2 = redisService.setString("ScanSucceed-QrId-" + qrId, token, 10, TimeUnit.SECONDS);
        boolean isSave3 = redisService.setString("userId-" + userId + "-token", token, 6, TimeUnit.HOURS);
        if (isSave && isSave2 && isSave3) {
            return Result.SUCCEED(token);
        } else {
            return Result.FAILED("二维码已过期");
        }
    }

    private enum ScanStatus {UnScan, Scaned, LoginSuccess}

}