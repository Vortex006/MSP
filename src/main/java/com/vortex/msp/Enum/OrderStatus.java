package com.vortex.msp.Enum;

public enum OrderStatus {

    /**
     * 已创建
     */
    Created,

    /**
     * 已取消
     */
    Cancelled,

    /**
     * 已关闭
     */
    Closed,

    /**
     * 未支付
     */
    Unpaid,

    /**
     * 待支付
     */
    PendingPaid,

    /**
     * 已支付
     */
    Paid,

    /**
     * 取消支付
     */
    CancelPaid,

    /**
     * 无需退款
     */
    NoRefund,

    /**
     * 待退款
     */
    PendingRefund,

    /**
     * 退款中
     */
    Refunding,

    /**
     * 已退款
     */
    Refunded,
}
