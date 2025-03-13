package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Uss;
import com.vortex.msp.Mapper.ussMapper;
import com.vortex.msp.Service.UssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UssServiceImpl implements UssService {

    @Autowired
    private ussMapper ussMapper1;


    @Override
    public Map<String, Integer> saveUss(Uss uss) {
        Integer saveCount = ussMapper1.saveUss(uss);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("rowCount", saveCount);
        map.put("ussId", uss.getUssId());
        return map;
    }
}
