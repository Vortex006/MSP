package com.zyj.msp.Controller;

import com.zyj.msp.Entity.LoginCredentials;
import com.zyj.msp.Entity.Patient;
import com.zyj.msp.Entity.RegisterUser;
import com.zyj.msp.Entity.User;
import com.zyj.msp.ExcelEntity.DemoEntity;
import com.zyj.msp.Service.PatientService;
import com.zyj.msp.Service.UserService;
import com.zyj.msp.Utils.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final PatientService patientService;

    public LoginController(UserService userService, PatientService patientService) {
        this.userService = userService;
        this.patientService = patientService;
    }

    @GetMapping("/{userId}")
    public Result getUser(@PathVariable("userId") Integer userId) {
        User user = userService.getUser(userId);
        return Result.SUCCEED(user);
    }

    @GetMapping("/codeImg")
    public Result getCode(HttpSession session) {
        String verificationCode = DataUtil.getVerificationCode();
        System.out.println("verificationCode:" + verificationCode);
        session.setAttribute("verificationCode", verificationCode);
        String imgBase64 = DataUtil.getVerificationCodeImg(verificationCode);
        return Result.SUCCEED(imgBase64);
    }

    @GetMapping("/check")
    public Result check(@Valid @RequestBody LoginCredentials loginCredentials) {
        return Result.FAILED();
    }

    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginCredentials loginCredentials, HttpServletRequest request) {
        String verificationCode = (String) request.getSession().getAttribute("verificationCode");
        String code = loginCredentials.getCode();
        if (!code.equals(verificationCode)) {
            return Result.FAILED("验证码错误");
        }
        User user = userService.getUserByName(loginCredentials.getUserName());
        if (Objects.isNull(user)) {
            return Result.FAILED("用户名或密码错误");
        }
        if (user.getUserPassword().equals(loginCredentials.getPassword())) {
            return Result.SUCCEED(TokenUtil.getToken(user.getUserId(), user.getUserName()));
        } else {
            return Result.FAILED("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result register(@Valid @RequestBody RegisterUser registerUser) {
        String username = registerUser.getUserName();
        String uuid = DataUtil.getUUID();
        User user = userService.getUserByName(username);
        if (Objects.nonNull(user)) {
            return Result.FAILED("用户名已被使用 换一个吧");
        }
        user = new User();
        user.setUserName(username);
        user.setUserPassword(EncryptUtil.encrypted(registerUser.getPassword(), uuid));
        user.setUserSalt(uuid);
        user.setUserState(true);
        user.setUserFlag(2);
        String certNo = registerUser.getCertNo();
        Patient patient = new Patient();
        patient.setPatientCertNo(certNo);
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
        String token = TokenUtil.getToken(user1.getUserId(), user1.getUserName());
        return Result.SUCCEED(token);
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
}
