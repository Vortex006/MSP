package com.zyj.msp.Mapper;

import com.zyj.msp.Entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientMapper {

    int savePatient(Patient patient);

    int updatePatient(Patient patient);

    int deletePatient(Integer patientId);

    Patient getPatient(Integer patientId);

    Patient getPatientByCertNo(String certNo);

    List<Patient> listPatients();

    List<Patient> limitPatients(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

}
