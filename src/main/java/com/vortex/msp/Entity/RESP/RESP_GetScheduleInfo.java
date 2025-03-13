package com.vortex.msp.Entity.RESP;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RESP_GetScheduleInfo extends RESP_Base {

    /**
     * 排班ID
     */
    private Integer scheduleId;

    /**
     * 排班所属科室
     */
    private Integer deptmentId;

    /**
     * 排班所属医生
     */
    private Integer doctorId;

    /**
     * 排班日期
     */
    private LocalDate scheduleDate;

    /**
     * 排班开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT+8")
    private LocalTime startTime;

    /**
     * 排班结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT+8")
    private LocalTime endTime;

    /**
     * 排班类型
     */
    private Integer scheduleType;

    /**
     * 号源数
     */
    private Integer scheduleNum;

}
