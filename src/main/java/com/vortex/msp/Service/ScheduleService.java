package com.vortex.msp.Service;

import com.vortex.msp.Entity.Schedule;
import com.vortex.msp.Entity.VO.ScheduleVo;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    Boolean saveSchedule(Schedule schedule);

    List<Schedule> listSchedules();

    Schedule getSchedule(Integer scheduleId);

    List<Schedule> getScheduleByDate(LocalDate date);

    List<Schedule> getScheduleByDates(List<LocalDate> dates);

    Integer getScheduleCount();

    List<Schedule> limitSchedules(Integer pageSize, Integer index);

    ScheduleVo DoToVo(Schedule schedule);

}
