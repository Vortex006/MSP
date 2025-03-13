package com.vortex.msp.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Schema(description = "患者实体类")
public class Patient extends EntityBase {

    /**
     * 患者ID
     */
    @Schema(description = "患者ID")
    private Integer patientId;

    /**
     * 患者就诊卡号
     */
    @Schema(description = "患者就诊卡号")
    private String patientCardNo;

    /**
     * 患者姓名
     */
    @Schema(description = "患者就诊卡号")
    private String patientName;

    /**
     * 患者性别
     */
    @Schema(description = "患者就诊卡号")
    private Integer patientSex;

    /**
     * 患者年龄
     */
    @Schema(description = "患者就诊卡号")
    private Integer patientAge;

    /**
     * 患者电话
     */
    @Schema(description = "患者电话")
    private String patientPhone;

    /**
     * 患者生日
     */
    @Schema(description = "患者生日")
    private LocalDate patientBirthday;

    /**
     * 患者身份证号
     */
    @Schema(description = "患者身份证号")
    private String patientCertNo;

    /**
     * 患者民族
     */
    @Schema(description = "患者民族")
    private String patientEthnicity;

    /**
     * 患者详细地址
     */
    @Schema(description = "患者详细地址")
    private String patientPlace;
}