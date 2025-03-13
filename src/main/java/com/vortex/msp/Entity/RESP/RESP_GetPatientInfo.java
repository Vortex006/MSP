package com.vortex.msp.Entity.RESP;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RESP_GetPatientInfo extends RESP_Base {

    /**
     * 患者ID
     */
    private Integer patientId;

    /**
     * 患者就诊卡号
     */
    private String cardNo;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 患者性别
     */
    private Integer sex;

    /**
     * 患者年龄
     */
    private Integer age;

    /**
     * 患者电话
     */
    private String mobile;

    /**
     * 患者生日
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    /**
     * 患者身份证号
     */
    private String certNo;

    /**
     * 患者民族
     */
    private String ethnicity;

    /**
     * 患者详细地址
     */
    private String address;

}
