package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Version;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VersionMapper {

    int saveVersion(Version version);

//    List<Version> listVersions();


    List<Version> limitVersions(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    Integer countVersions();

    /**
     * 更新所有版本的latest字段为false
     *
     * @param versionType 版本类型
     */
    void updateAllLatestFalse(Integer versionType);

    /**
     * 获取最新的版本信息
     *
     * @param versionType 版本类型
     * @return 最新版本信息
     */
    Version getLatestVersion(Integer versionType);

}
