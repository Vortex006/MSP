package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Dict;
import com.vortex.msp.Mapper.DictMapper;
import com.vortex.msp.Service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    private final DictMapper dictMapper;

    @Autowired
    public DictServiceImpl(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public List<Dict> getDictByType(String dictType) {
        List<Dict> dictByType = dictMapper.getDictByType(dictType);

        return dictByType;
    }
}
