package com.zyj.msp.ServiceImpl;

import com.zyj.msp.Entity.Sche;
import com.zyj.msp.Mapper.ScheMapper;
import com.zyj.msp.Service.ScheService;
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
