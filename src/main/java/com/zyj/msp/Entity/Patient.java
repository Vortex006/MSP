package com.zyj.msp.Entity;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Patient extends EntityBase {

    /**
     * 患者ID
     */
    private Integer patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者性别
     */
    private String patientSex;

    /**
     * 患者年龄
     */
    private Integer patientAge;

    /**
     * 患者电话
     */
    private String patientPhone;

    /**
     * 患者生日
     */
    private LocalDate patientBirthday;

    /**
     * 患者身份证号
     */
    private String patientCertNo;

    /**
     * 患者详细地址
     */
    private String patientPlace;

}

