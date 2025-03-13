package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Doctor;
import com.vortex.msp.Mapper.DoctorMapper;
import com.vortex.msp.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorServiceImpl(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public List<Doctor> listDoctors() {
        List<Doctor> doctorList = doctorMapper.listDoctors();
        return doctorList;
    }

    @Override
    public Doctor getDoctor(Integer doctorId) {
        Doctor doctor = doctorMapper.getDoctor(doctorId);
        return doctor;
    }

    @Override
    public List<Doctor> getDoctors(List<Integer> doctorIds) {
        List<Doctor> doctorList = doctorMapper.getDoctors(doctorIds);
        return doctorList;
    }

    /**
     * 根据医生姓名获取医生信息
     *
     * @param doctorName 医生姓名
     * @return 医生信息集合
     */
    @Override
    public List<Doctor> getDoctorByName(String doctorName) {
        List<Doctor> doctorList = doctorMapper.getDoctorByName(doctorName);
        return doctorList;
    }
}
