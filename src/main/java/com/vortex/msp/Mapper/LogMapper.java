package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    int saveLog(LogEntity log);

}
