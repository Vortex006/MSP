package com.zyj.msp.Entity;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reserve extends EntityBase {

    /**
     * 预约ID，主键，自动增长
     */
    private Integer reserveId;

    /**
     * 预约用户名，不允许为空，默认值为'无'
     */
    private String reserveUsername;

    /**
     * 预约时间，不允许为空，默认为当前时间
     */
    private Date reserveDate;

    /**
     * 预约科室，不允许为空，默认值为0
     */
    private Integer reserveRoom;

    /**
     * 预约医生，不允许为空，默认值为0
     */
    private Integer reserveDoctor;

    /**
     * 留存手机号，不允许为空，默认值为'99999999999'
     */
    private String reservePhone;

    /**
     * 预约状态，不允许为空，默认值为0
     */
    private Byte reserveStatus;

    /**
     * 创建人，允许为空
     */
    private String createUser;
}
