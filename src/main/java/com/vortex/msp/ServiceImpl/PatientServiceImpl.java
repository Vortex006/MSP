package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Patient;
import com.vortex.msp.Entity.RESP.RESP_GetPatientInfo;
import com.vortex.msp.Enum.CardType;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Mapper.PatientMapper;
import com.vortex.msp.Service.PatientService;
import com.vortex.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientMapper patientMapper;

    @Autowired
    public PatientServiceImpl(PatientMapper patientMapper) {
        this.patientMapper = patientMapper;
    }

    @Override
    public boolean savePatient(Patient patient) {
        int i = patientMapper.savePatient(patient);
        return i > 0;
    }

    @Override
    public boolean updatePatient(Patient patient) {
        int i = patientMapper.updatePatient(patient);
        return i > 0;
    }

    @Override
    public boolean deletePatient(Integer patientId) {
        int i = patientMapper.deletePatient(patientId);
        return i > 0;
    }

    @Override
    public Patient getPatient(Integer patientId) {
        Patient patient = patientMapper.getPatient(patientId);
        return patient;
    }

    @Cacheable(value = "patientCache", key = "{#cardType,#cardNo}")
    @Override
    public RESP_GetPatientInfo getPatientInfoByTerminal(Integer cardType, String cardNo) {
        String columnName = CardType.findColName(cardType);
        if (!StringUtils.hasText(columnName)) {
            throw new ParameterNullException("不支持的卡类型");
        }
        Patient patient = patientMapper.getPatientInfoByTerminal(columnName, cardNo);
        RESP_GetPatientInfo patientInfo = new RESP_GetPatientInfo();
        patientInfo.setPatientId(patient.getPatientId());
        patientInfo.setCardNo(patient.getPatientCardNo());
        patientInfo.setCertNo(patient.getPatientCertNo());
        patientInfo.setName(patient.getPatientName());
        patientInfo.setAge(patient.getPatientAge());
        patientInfo.setSex(patient.getPatientSex());
        patientInfo.setBirthday(patient.getPatientBirthday());
        patientInfo.setMobile(patient.getPatientPhone());
        patientInfo.setEthnicity(patient.getPatientEthnicity());
        patientInfo.setAddress(patient.getPatientPlace());
        return patientInfo;
    }

    @Override
    public Patient getPatientByCertNo(String certNo) {
        Patient patient = patientMapper.getPatientByCertNo(certNo);
        return patient;
    }

    @Override
    public List<Patient> listPatients() {
        List<Patient> patients = patientMapper.listPatients();
        return patients;
    }

    @Override
    public List<Patient> limitPatients(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        List<Patient> patients = patientMapper.limitPatients(pageSize, offset);
        return patients;
    }
}
