package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppointmentMapper {

    List<Appointment> getAppointmentBySchedule(Integer scheduleId);

    List<Appointment> getAppointmentByPatient(String patientId);

    List<Appointment> listAppointments();

    Appointment getAppointment(Integer appointmentId);

    Integer saveAppointment(Appointment appointment);

    Map<String, String> getAppointmentInfo(Integer appointmentId);

    Integer getAppointmentId(@Param("scheduleId") Integer scheduleId, @Param("patientId") String patientId);

}
