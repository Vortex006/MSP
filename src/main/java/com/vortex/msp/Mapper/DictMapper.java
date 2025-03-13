package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictMapper {

    List<Dict> getDictByType(String dictType);

}
