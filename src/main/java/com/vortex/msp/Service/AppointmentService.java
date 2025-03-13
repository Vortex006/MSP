package com.vortex.msp.Service;

import com.vortex.msp.Entity.Appointment;
import com.vortex.msp.Enum.AppointmentStates;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    List<Appointment> getAppointmentBySchedule(Integer scheduleId, AppointmentStates state);

    List<Appointment> getAppointmentByPatient(String patientId, AppointmentStates state);

    List<Appointment> listAppointments(AppointmentStates state);

    Appointment getAppointment(Integer appointmentId);

    Map<String, Integer> saveAppointment(Appointment appointment);

    Map<String, String> getAppointmentInfo(Integer appointmentId);
}
