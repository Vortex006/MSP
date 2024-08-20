package com.zyj.msp.Entity;

import lombok.*;

import java.time.LocalDate;

/**
 * 排班表实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Sche extends EntityBase {

    /**
     * 排班ID
     */
    private Integer scheId;

    /**
     * 排班科室
     */
    private Integer scheRoom;

    /**
     * 排班医生
     */
    private Integer scheDoctor;

    /**
     * 排班日期
     */
    private LocalDate scheDate;

    /**
     * 排班标识：1-上午，2-下午，3-晚上，4-全天
     */
    private Integer scheFlag;

    /**
     * 排班总号数
     */
    private Integer scheTotalNum;

    /**
     * 排班已挂号号数
     */
    private Integer scheRegNum;

}
