package com.vortex.msp.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "排班实体类")
public class Schedule extends EntityBase {

    /**
     * 排班ID
     */
    private Integer scheduleId;

    /**
     * 排班所属科室
     */
    private Integer scheduleDeptmentId;

    /**
     * 排班所属医生
     */
    private Integer scheduleDoctorId;

    /**
     * 排班日期
     */
    private LocalDate scheduleDate;

    /**
     * 排班开始时间
     */
    private LocalTime scheduleStartTime;

    /**
     * 排班结束时间
     */
    private LocalTime scheduleEndTime;

    /**
     * 排班类型
     */
    private Integer scheduleType;

    /**
     * 号源数
     */
    private Integer scheduleNum;

}
