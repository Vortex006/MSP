package com.vortex.msp.Entity.RESP;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RESP_GetScheduleDoctor extends RESP_Base {

    /**
     * 科室ID
     */
    Integer deptmentId;

    /**
     * 医生ID
     */
    Integer doctorId;

    /**
     * 医生姓名
     */
    String doctorName;

    /**
     * 医生职称
     */
    String doctorTitle;

    /**
     * 挂号费
     */
    BigDecimal regFee;

}
