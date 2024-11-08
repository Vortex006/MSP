package com.vortex.msp.Entity;

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
     * 患者就诊卡号
     */
    private String patientCardNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者性别
     */
    private Integer patientSex;

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
     * 患者民族
     */
    private String patientEthnicity;

    /**
     * 患者详细地址
     */
    private String patientPlace;
}