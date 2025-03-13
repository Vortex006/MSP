package com.vortex.msp.Service;

import com.vortex.msp.Entity.Version;

import java.util.List;

public interface VersionService {

    /**
     * 保存版本信息
     *
     * @param version 版本信息
     * @return 是否成功 true-成功 false-失败
     */
    boolean saveVersion(Version version);

    /**
     * 分页查询版本信息
     *
     * @param pageSize 每页显示条数
     * @param index    当前页码
     * @return 版本信息集合
     */
    List<Version> limitVersions(Integer pageSize, Integer index);

    /**
     * 获取版本信息总数
     *
     * @return 版本信息总数
     */
    Integer countVersions();

    /**
     * 设置指定版本类型的版本信息为非最新
     *
     * @param versionType 版本类型
     */
    void updateAllLatestFalse(Integer versionType);

    /**
     * 获取指定版本类型的最新版本信息
     *
     * @param versionType 版本类型
     * @return 版本信息
     */
    Version getLatestVersion(Integer versionType);

}
