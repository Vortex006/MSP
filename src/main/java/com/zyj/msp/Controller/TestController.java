package com.zyj.msp.Controller;

import com.zyj.msp.ServiceImpl.TestServiceImpl;
import com.zyj.msp.Utils.DataUtil;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    private TestServiceImpl testService;

    @GetMapping
    public Result hello1(@RequestParam("cardNo") String cardNo, @RequestParam("cardType") String cardType) {
//        CompletableFuture<String> future = testService.test();
//        future.thenAccept(System.out::println)
//                .exceptionally(e -> {
//                    e.printStackTrace();
//                    return null;
//                });
        System.out.println(cardNo);
        System.out.println(cardType);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "18");
        return Result.SUCCEED(map);
    }

    @PostMapping
    public Result hello2() {
        return Result.SUCCEED("Hello POST");
    }

    @PutMapping
    public Result hello3() {
        return Result.SUCCEED("Hello PUT");
    }

    @DeleteMapping
    public Result hello4() {
        return Result.SUCCEED("Hello DELETE");
    }

    @GetMapping("WX")
    public Result WxPayQrCode() {
        String url = "http://192.168.10.7:9090/#/user8";
        return Result.SUCCEED(DataUtil.getWxPayQrCode(url));
    }

    @GetMapping("Ali")
    public Result AliPayQrCode() {
        String url = "http://192.168.10.7:9191/test";
        return Result.SUCCEED(DataUtil.getAliPayQrCode(url));
    }

}
