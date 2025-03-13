package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Appointment;
import com.vortex.msp.Enum.AppointmentStates;
import com.vortex.msp.Mapper.AppointmentMapper;
import com.vortex.msp.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentMapper appointmentMapper) {
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public List<Appointment> getAppointmentBySchedule(Integer scheduleId, AppointmentStates state) {
        List<Appointment> appointmentList = appointmentMapper.getAppointmentBySchedule(scheduleId);
        if (state == AppointmentStates.ALL) {
            return appointmentList;
        } else {
            List<Appointment> appointments =
                    appointmentList.stream().filter(appointment -> appointment.getAppointmentState().equals(state.getCode())).collect(Collectors.toList());
            return appointments;
        }
    }

    @Override
    public List<Appointment> getAppointmentByPatient(String patientId, AppointmentStates state) {
        List<Appointment> appointmentList = appointmentMapper.getAppointmentByPatient(patientId);
        if (state == AppointmentStates.ALL) {
            return appointmentList;
        } else {
            List<Appointment> appointments = getStatesAppointment(appointmentList, state);
            return appointments;
        }
    }

    @Override
    public List<Appointment> listAppointments(AppointmentStates state) {
        List<Appointment> appointmentList = appointmentMapper.listAppointments();
        if (state == AppointmentStates.ALL) {
            return appointmentList;
        } else {
            List<Appointment> appointments = getStatesAppointment(appointmentList, state);
            return appointments;
        }
    }

    @Override
    public Appointment getAppointment(Integer appointmentId) {
        Appointment appointment = appointmentMapper.getAppointment(appointmentId);
        return appointment;
    }

    @Override
    public Map<String, Integer> saveAppointment(Appointment appointment) {
        Integer isSave = appointmentMapper.saveAppointment(appointment);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("rowCount", isSave);
        map.put("appointmentId", appointment.getAppointmentId());
        return map;
    }

    @Override
    public Map<String, String> getAppointmentInfo(Integer appointmentId) {
        Map<String, String> appointmentInfo = appointmentMapper.getAppointmentInfo(appointmentId);
        return appointmentInfo;
    }

    private List<Appointment> getStatesAppointment(List<Appointment> appointmentList, AppointmentStates state) {
        List<Appointment> appointments =
                appointmentList.stream().filter(appointment -> appointment.getAppointmentState().equals(state.getCode())).collect(Collectors.toList());
        return appointments;
    }

}
