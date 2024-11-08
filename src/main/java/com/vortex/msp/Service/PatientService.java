package com.vortex.msp.Service;

import com.vortex.msp.Entity.Patient;

import java.util.List;

public interface PatientService {

    boolean savePatient(Patient patient);

    boolean updatePatient(Patient patient);

    boolean deletePatient(Integer patientId);

    Patient getPatient(Integer patientId);

    Patient getPatientZZJ(Integer cardType, String cardNo);

    Patient getPatientByCertNo(String certNo);

    List<Patient> listPatients();

    List<Patient> limitPatients(Integer pageSize, Integer index);
}
