package com.zyj.msp.Mapper;

import com.zyj.msp.Entity.Sche;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheMapper {

    int saveSche(Sche sche);
}
