package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Doctor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorMapper {

    List<Doctor> listDoctors();

    Doctor getDoctor(Integer doctorId);

    List<Doctor> getDoctors(List<Integer> doctorIds);

    List<Doctor> getDoctorByName(String doctorName);
}
