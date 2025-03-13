package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Version;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Mapper.VersionMapper;
import com.vortex.msp.Service.VersionService;
import com.vortex.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {

    private final VersionMapper versionMapper;

    @Autowired
    public VersionServiceImpl(VersionMapper versionMapper) {
        this.versionMapper = versionMapper;
    }


    @Override
    public boolean saveVersion(Version version) {
        //如果当前版本为最新版本，则将之前版本的latest字段设置为false
        if (version.getLatest()) {
            updateAllLatestFalse(version.getVersionType());
        }
        int isSave = versionMapper.saveVersion(version);
        return isSave > 0;
    }

    @Override
    public List<Version> limitVersions(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        List<Version> versions = versionMapper.limitVersions(pageSize, offset);
        return versions;
    }

    @Override
    public Integer countVersions() {
        Integer count = versionMapper.countVersions();
        return count;
    }

    @Override
    public void updateAllLatestFalse(Integer versionType) {
        versionMapper.updateAllLatestFalse(versionType);
    }

    @Override
    public Version getLatestVersion(Integer versionType) {
        Version version = versionMapper.getLatestVersion(versionType);
        return version;
    }
}
