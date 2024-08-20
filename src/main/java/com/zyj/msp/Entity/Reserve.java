package com.zyj.msp.Entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Reserve extends EntityBase {

    /**
     * 挂号ID
     */
    private Integer reserveId;

    /**
     * 挂号排班
     */
    private Integer reserveSche;

    /**
     * 挂号患者
     */
    private Integer reservePatient;

    /**
     * 挂号科室
     */
    private Integer reserveRoom;

    /**
     * 挂号医生
     */
    private Integer reserveDoctor;

    /**
     * 挂号日期
     */
    private LocalDate reserveDate;

    /**
     * 挂号状态
     */
    private Integer reserveStatus;

    /**
     * 挂号金额
     */
    private BigDecimal reserveMoney;

    /**
     * 支付方式
     */
    private Integer reservePayType;

    /**
     * 支付状态
     */
    private Integer reservePayStatus;

    /**
     * 留存手机号
     */
    private String reservePhone;

    /**
     * 留存邮箱
     */
    private String reserveEmail;

}
