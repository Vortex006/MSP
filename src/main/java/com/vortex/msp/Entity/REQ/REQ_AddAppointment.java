package com.vortex.msp.Entity.REQ;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class REQ_AddAppointment extends REQ_Base {

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
