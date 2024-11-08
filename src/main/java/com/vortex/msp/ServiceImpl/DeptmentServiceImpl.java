package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Deptment;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Mapper.DeptmentMapper;
import com.vortex.msp.Service.DeptmentService;
import com.vortex.msp.Utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptmentServiceImpl implements DeptmentService {
    private final DeptmentMapper deptmentMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DeptmentServiceImpl(DeptmentMapper deptmentMapper) {
        this.deptmentMapper = deptmentMapper;
    }

    @Override
    public boolean saveDeptment(Deptment deptment) {
        int i = deptmentMapper.saveDeptment(deptment);
        return i > 0;
    }

    @Override
    public boolean updateDeptment(Deptment deptment) {
        int i = deptmentMapper.updateDeptment(deptment);
        return i > 0;
    }

    @Override
    public boolean deleteDeptment(Integer deptmentId) {
        int i = deptmentMapper.deleteDeptment(deptmentId);
        return i > 0;
    }

    @Override
    public Deptment getDeptment(Integer deptmentId) {
        Deptment deptment = deptmentMapper.getDeptment(deptmentId);
        return deptment;
    }

    @Override
    public List<Deptment> getDeptmentsByName(String deptmentName) {
        List<Deptment> deptments = deptmentMapper.getDeptmentsByName(deptmentName);
        return deptments;
    }

    @Override
    public Integer getDeptmentCount() {
        Integer count = deptmentMapper.getDeptmentCount();
        return count;
    }

    @Override
    public List<Deptment> listDeptments() {
        List<Deptment> deptments = deptmentMapper.listDeptments();
        return deptments;
    }

    @Override
    public List<Deptment> limitDeptments(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        List<Deptment> deptments = deptmentMapper.limitDeptments(pageSize, offset);
        return deptments;
    }

    @Override
    public List<Deptment> getDeptmentsByZZJ(Integer deptType, Integer deptParent) {
        List<Deptment> deptments = deptmentMapper.getDeptmentsByZZJ(deptType, deptParent);
        return deptments;
    }
}
