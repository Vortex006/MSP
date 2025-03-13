package com.vortex.msp.Controller;

import com.vortex.msp.Entity.LoginCredentials;
import com.vortex.msp.Entity.Patient;
import com.vortex.msp.Entity.RegisterUser;
import com.vortex.msp.Entity.User;
import com.vortex.msp.ExcelEntity.DemoEntity;
import com.vortex.msp.Service.EmailService;
import com.vortex.msp.Service.PatientService;
import com.vortex.msp.Service.RedisService;
import com.vortex.msp.Service.UserService;
import com.vortex.msp.Utils.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
@Tag(name = "登录模块")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final PatientService patientService;
    private final EmailService emailService;
    private final RedisService redisService;
    private long timeout = 6 * 60 * 60 * 1000; //6小时

    @Autowired
    public LoginController(UserService userService, PatientService patientService, EmailService emailService,
                           RedisService redisService) {
        this.userService = userService;
        this.patientService = patientService;
        this.emailService = emailService;
        this.redisService = redisService;
    }

    @GetMapping("/{userId}")
    public Result getUser(@PathVariable("userId") Integer userId) {
        User user = userService.getUser(userId);
        return Result.SUCCEED(user);
    }

    @GetMapping("/codeImg")
    public Result getCode(HttpSession session) {
        String verificationCode = DataUtil.getVerificationCode();
        logger.debug("verificationCode:{}", verificationCode);
        session.setAttribute("verificationCode", verificationCode);
        String imgBase64 = DataUtil.getVerificationCodeImg(verificationCode);
        return Result.SUCCEED(imgBase64);
    }

    @GetMapping("/check")
    public Result check(@Valid @RequestBody LoginCredentials loginCredentials) {
        return Result.FAILED();
    }

    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginCredentials loginCredentials, HttpSession session) {
        String verificationCode = (String) session.getAttribute("verificationCode");
        String code = loginCredentials.getCode();
        if (!code.equals(verificationCode)) {
            return Result.FAILED("验证码错误");
        }
        User user = userService.getUserByName(loginCredentials.getUserName());
        if (Objects.isNull(user)) {
            return Result.FAILED("用户名或密码错误");
        }
        String db_Password = EncryptUtil.decrypted(user.getUserPassword(), user.getUserSalt());
        if (db_Password.equals(loginCredentials.getPassword())) {

            HashMap<String, String> map = new HashMap<>();
            map.put("userId", user.getUserId().toString());
            String token = TokenUtil.getToken(map);
            String key = "userId-" + user.getUserId() + "-token";
            Boolean isRedisSuccess = redisService.setString(key, token, 6, TimeUnit.HOURS);
            Map<String, String> result = new HashMap<>();
            result.put("AccessToken", token);
//            result.put("ExpiryTime", String.valueOf(System.currentTimeMillis() + ()));
            if (isRedisSuccess) {
                return Result.SUCCEED(token);
            } else {
                return Result.FAILED("redis存储token失败");
            }
        } else {
            return Result.FAILED("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result register(@Valid @RequestBody RegisterUser registerUser, HttpSession session) {
        String verificationCode = (String) session.getAttribute("verificationCode");
        String code = registerUser.getCode();
        if (!code.equals(verificationCode)) {
            return Result.FAILED("验证码错误");
        }
        String username = registerUser.getUserName();
        String uuid = DataUtil.getUUID();
        User user = userService.getUserByName(username);
        if (Objects.nonNull(user)) {
            return Result.FAILED("用户名已被占用");
        }
        user = new User();
        user.setUserName(username);
        user.setUserPassword(EncryptUtil.encrypted(registerUser.getPassword(), uuid));
        user.setUserSalt(uuid);
        user.setUserState(true);
        user.setUserFlag(2);
        String certNo = registerUser.getCertNo();
        Patient patient = new Patient();
        patient.setPatientCardNo(certNo);
        patient.setPatientCertNo(certNo);
        patient.setPatientSex(1);
        patient.setPatientAge(DataUtil.getAgeByCertNo(certNo));
        patient.setPatientName(registerUser.getName());
        patient.setPatientBirthday(DataUtil.getBirthDateByCertNo(certNo));
        patient.setPatientPhone(registerUser.getUserPhone());
        boolean isPatientSave = patientService.savePatient(patient);
        if (!isPatientSave) {
            return Result.FAILED("注册失败");
        }
        int patientId = patientService.getPatientByCertNo(certNo).getPatientId();
        user.setUserRelevanceId(patientId);
        boolean isUserSave = userService.saveUser(user);
        if (!isUserSave) {
            return Result.FAILED("注册失败");
        }
        User user1 = userService.getUserByName(username);
        if (!Objects.nonNull(user1)) {
            return Result.FAILED("注册失败");
        }
        return Result.SUCCEED();
    }

    @GetMapping("/getExcel")
    public Result getExcel() {
        String path = ExcelUtil.getExcel(DemoEntity.class, data());
        File file = new File(path);
        FileInputStream fs = null;
        byte[] bytes = new byte[0];
        try {
            fs = new FileInputStream(path);
            bytes = new byte[fs.available()];
            int read = fs.read(bytes);
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.SUCCEED(bytes);
    }

    private List<DemoEntity> data() {
        List<DemoEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DemoEntity d = new DemoEntity();
            d.setUserCreate(i);
            d.setUserUpdate(i + 1);
            list.add(d);
        }
        return list;
    }

    @GetMapping("/mail")
    public Result sendMail() {
        String code = DataUtil.getVerificationCode();
        String emaliHtml = EmailUtil.buildContent(code);
        emailService.sendTextMailMessage("3409212131@qq.com", "这只是一个测试", emaliHtml);
        return Result.SUCCEED();
    }

    @GetMapping("/token/{qrId}")
    public Result GetScanQrCodeToken(@PathVariable("qrId") String qrId) {
        String token = String.valueOf(redisService.getString("ScanSucceed-QrId-" + qrId));
        if (Objects.nonNull(token)) {
            return Result.SUCCEED(token);
        } else {
            return Result.FAILED();
        }
    }

}