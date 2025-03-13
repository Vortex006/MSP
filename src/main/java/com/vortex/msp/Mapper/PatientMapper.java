package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientMapper {

    int savePatient(Patient patient);

    int updatePatient(Patient patient);

    int deletePatient(Integer patientId);

    Patient getPatient(Integer patientId);

    Patient getPatientInfoByTerminal(@Param("columnName") String columnName, @Param("cardNo") String cardNo);

    Patient getPatientByCertNo(String certNo);

    List<Patient> listPatients();

    List<Patient> limitPatients(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

}
