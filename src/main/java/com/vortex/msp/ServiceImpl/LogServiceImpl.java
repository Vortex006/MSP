package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.LogEntity;
import com.vortex.msp.Mapper.LogMapper;
import com.vortex.msp.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogMapper logMapper;

    @Autowired
    public LogServiceImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public boolean saveLog(LogEntity log) {
        int isSave = logMapper.saveLog(log);
        return isSave > 0;
    }
}
