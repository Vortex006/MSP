package com.vortex.msp.Entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleVo {

    /**
     * 排班ID
     */
    private Integer scheduleId;

    /**
     * 排班所属科室
     */
    private String scheduleDeptment;

    /**
     * 排班所属医生
     */
    private String scheduleDoctor;

    /**
     * 排班日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate scheduleDate;

    /**
     * 排班开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT+8")
    private LocalTime scheduleStartTime;

    /**
     * 排班结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT+8")
    private LocalTime scheduleEndTime;

    /**
     * 排班类型
     */
    private String scheduleType;

    /**
     * 号源数
     */
    private Integer scheduleNum;
}
