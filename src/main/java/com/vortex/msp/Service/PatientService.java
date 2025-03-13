package com.vortex.msp.Service;

import com.vortex.msp.Entity.Patient;
import com.vortex.msp.Entity.RESP.RESP_GetPatientInfo;

import java.util.List;

public interface PatientService {

    boolean savePatient(Patient patient);

    boolean updatePatient(Patient patient);

    boolean deletePatient(Integer patientId);

    Patient getPatient(Integer patientId);

    RESP_GetPatientInfo getPatientInfoByTerminal(Integer cardType, String cardNo);

    Patient getPatientByCertNo(String certNo);

    List<Patient> listPatients();

    List<Patient> limitPatients(Integer pageSize, Integer index);
}
