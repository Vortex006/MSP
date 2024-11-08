package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Sche;
import com.vortex.msp.Mapper.ScheMapper;
import com.vortex.msp.Service.ScheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheServiceImpl implements ScheService {

    private final ScheMapper schemaMapper;

    @Autowired
    public ScheServiceImpl(ScheMapper schemaMapper) {
        this.schemaMapper = schemaMapper;
    }

    @Override
    public boolean saveSche(Sche sche) {
        int i = schemaMapper.saveSche(sche);
        return i > 0;
    }
}
