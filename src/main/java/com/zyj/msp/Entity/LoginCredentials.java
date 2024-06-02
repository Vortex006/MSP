package com.zyj.msp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginCredentials {
    @Length(min = 6, max = 19, message = "用户名长度是6-18位")
    private String userName;
    @Length(min = 6, max = 19, message = "密码长度是6-18位")
    private String password;
    @Length(min = 4, max = 4, message = "验证码长度是4位")
    private String code;
}
