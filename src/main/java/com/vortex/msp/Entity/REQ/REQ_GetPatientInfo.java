package com.vortex.msp.Entity.REQ;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class REQ_GetPatientInfo extends REQ_Base {

    /**
     * 患者卡类型
     */
    @NotBlank(message = "患者卡类型不能为空")
    private String cardType;

    /**
     * 患者卡号
     */
    @NotBlank(message = "患者卡号不能为空")
    private String cardNo;

    /**
     * 患者姓名
     */
    private String patientName;

}