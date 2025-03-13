package com.vortex.msp.Service;

import com.vortex.msp.Entity.Doctor;

import java.util.List;

public interface DoctorService {

    /**
     * 获取所有医生信息
     *
     * @return 医生信息集合
     */
    List<Doctor> listDoctors();

    /**
     * 根据医生ID获取医生信息
     *
     * @param doctorId 医生ID
     * @return 医生信息
     */
    Doctor getDoctor(Integer doctorId);

    List<Doctor> getDoctors(List<Integer> doctorIds);

    /**
     * 根据医生姓名获取医生信息
     *
     * @param doctorName 医生姓名
     * @return 医生信息集合
     */
    List<Doctor> getDoctorByName(String doctorName);
}
