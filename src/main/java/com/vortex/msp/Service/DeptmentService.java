package com.vortex.msp.Service;


import com.vortex.msp.Entity.Deptment;

import java.util.List;

public interface DeptmentService {

    boolean saveDeptment(Deptment deptment);

    boolean updateDeptment(Deptment deptment);

    boolean deleteDeptment(Integer deptmentId);

    Deptment getDeptment(Integer deptmentId);

    List<Deptment> getDeptmentsByName(String deptmentName);

    Integer getDeptmentCount();

    List<Deptment> listDeptments();

    List<Deptment> limitDeptments(Integer pageSize, Integer index);

    List<Deptment> getDeptmentsByZZJ(Integer deptType, Integer deptParent);

}
