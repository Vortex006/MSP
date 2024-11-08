package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Sche;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheMapper {

    int saveSche(Sche sche);
}
