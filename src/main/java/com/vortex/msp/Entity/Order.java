package com.vortex.msp.Entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Order extends EntityBase {

    /**
     * 订单ID，自增主键
     */
    private Integer orderId;

    /**
     * 订单号，唯一键，非空
     */
    private Long orderNo;

    /**
     * 订单所属患者ID，非空
     */
    private Integer orderPatient;

    /**
     * 订单类型，非空
     */
    private Integer orderType;

    /**
     * 订单状态，非空
     */
    private Integer orderStatus;

    /**
     * 商品信息ID，非空
     */
    private Integer orderGood;

    /**
     * 订单总金额，非空，默认0.00
     */
    private BigDecimal orderMoney;

    /**
     * 订单支付类型，非空
     */
    private Integer orderPayType;

    /**
     * 订单支付状态，非空
     */
    private Integer orderPayStatus;

    /**
     * 订单支付金额，非空，默认0.00
     */
    private BigDecimal orderPayMoney;

    /**
     * 订单创建时间，非空，默认为当前时间
     */
    private LocalDateTime orderCreateDatetime;

    /**
     * 订单支付时间，非空
     */
    private LocalDateTime orderPayDatetime;

    /**
     * 订单关闭时间，非空
     */
    private LocalDateTime orderCloseDatetime;

}

