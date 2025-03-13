package com.vortex.msp.Service;

import com.vortex.msp.Entity.Dict;

import java.util.List;

public interface DictService {

    List<Dict> getDictByType(String dictType);

}
