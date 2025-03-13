package com.vortex.msp.Entity.REQ;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class REQ_LockSchedule extends REQ_Base {

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
