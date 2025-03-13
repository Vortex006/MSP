package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Version;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Service.AsyncService;
import com.vortex.msp.Service.VersionService;
import com.vortex.msp.Utils.DataUtil;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/version")
@Tag(name = "版本号模块")
public class VersionController {

    private final VersionService versionService;
    private final AsyncService asyncService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.local.root.path}")
    private String localFileRootPath;

    @Autowired
    public VersionController(VersionService versionService, AsyncService asyncService) {
        this.versionService = versionService;
        this.asyncService = asyncService;
    }

    @PostMapping
    public Result saveVersion(@RequestBody Version version) {
        if (ObjectUtils.isEmpty(version)) {
            throw new ParameterNullException("参数不能为空");
        }
        String fileName = version.getVersionResource();
        String localFilePath = DataUtil.getFilePath(localFileRootPath, "file", fileName);
        if (new File(localFilePath).exists()) {
            boolean isSave = versionService.saveVersion(version);
            if (!isSave) {
                return Result.FAILED("新增版本信息失败");
            }
            CompletableFuture<Boolean> booleanCompletableFuture = asyncService.uploadFtp(fileName, localFilePath);
            booleanCompletableFuture.thenAccept(result -> {
                if (result) {
                    logger.info("文件{}上传至FTP服务器成功", fileName);
                } else {
                    logger.error("文件{}上传至FTP服务器失败", fileName);
                }
            });
            return Result.SUCCEED();
        } else {
            logger.error("未能找到{}文件", localFilePath);
            return Result.FAILED("未能找到文件");
        }
    }

    @GetMapping("/limit/{pageSize}/{index}")
    public Result limitVersions(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Version> versions = versionService.limitVersions(pageSize, index);
        return Result.SUCCEED(versions);
    }

    @GetMapping("/count")
    public Result countVersions() {
        Integer count = versionService.countVersions();
        return Result.SUCCEED(count);
    }

    @GetMapping("/latest/{versionType}")
    public Result getLatestVersion(@PathVariable("versionType") Integer versionType) {
        Version latestVersion = versionService.getLatestVersion(versionType);
        logger.info(latestVersion.toString());
        return Result.SUCCEED(latestVersion);
    }

}
