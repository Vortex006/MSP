package com.vortex.msp.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "挂号实体类")
@EqualsAndHashCode(callSuper = true)
public class Appointment extends EntityBase {

    /**
     * 挂号ID
     */
    private Integer appointmentId;

    /**
     * 排班ID
     */
    private Integer scheduleId;

    /**
     * 患者ID
     */
    private Integer patientId;

    /**
     * 挂号状态
     */
    private Integer appointmentState;

}
