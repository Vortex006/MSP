package com.zyj.msp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterUser {
    private Integer userId;
    @Length(min = 6, max = 19, message = "用户名长度是6-18位")
    private String userName;
    private String name;
    @Length(min = 6, max = 19, message = "密码长度是6-18位")
    private String password;
    @Email
    private String userEmali;
    private String userPhone;
    private String certNo;
    private String code;

}
