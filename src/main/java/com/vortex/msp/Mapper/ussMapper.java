package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Uss;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ussMapper {
    Integer saveUss(Uss uss);
}
