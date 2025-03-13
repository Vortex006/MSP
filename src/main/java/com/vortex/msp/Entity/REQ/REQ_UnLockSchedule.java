package com.vortex.msp.Entity.REQ;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;


@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
public class REQ_UnLockSchedule extends REQ_Base {

    /**
     * 排班ID
     */
    @NotNull(message = "排班ID不能为空")
    private Integer scheduleId;

    /**
     * 患者ID
     */
    @NotNull(message = "患者ID不能为空")
    private Integer patientId;

}
