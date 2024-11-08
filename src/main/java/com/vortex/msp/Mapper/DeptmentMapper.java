package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Deptment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptmentMapper {

    Integer saveDeptment(Deptment deptment);

    Integer updateDeptment(Deptment deptment);

    Integer deleteDeptment(Integer deptmentId);

    Deptment getDeptment(Integer deptmentId);

    List<Deptment> getDeptmentsByName(String deptmentName);

    Integer getDeptmentCount();

    List<Deptment> listDeptments();

    List<Deptment> limitDeptments(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    List<Deptment> getDeptmentsByZZJ(@Param("deptType") Integer deptType, @Param("deptParent") Integer deptParent);

}
