package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    Integer saveSchedule(Schedule schedule);

    List<Schedule> listSchedules();

    Schedule getSchedule(Integer scheduleId);

    List<Schedule> getScheduleByDate(LocalDate date);

    List<Schedule> getScheduleByDates(List<LocalDate> dates);

    Integer getScheduleCount();

    List<Schedule> limitSchedules(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    //    String date = map.getOrDefault("date","");
//    String time = map.getOrDefault("time","");
//    String dept = map.getOrDefault("dept","");
//    String doctor = map.getOrDefault("doctor","");
//    String title = map.getOrDefault("title","");
    List<Schedule> test(@Param("date") String date, @Param("time") String time, @Param("dept") String dept, @Param(
            "doctor") String doctor, @Param("title") String title);

}
