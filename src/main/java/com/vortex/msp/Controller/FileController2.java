package com.vortex.msp.Controller;

import com.vortex.msp.Entity.MergeRequest;
import com.vortex.msp.Service.AsyncService;
import com.vortex.msp.Utils.DataUtil;
import com.vortex.msp.Utils.FtpUtil;
import com.vortex.msp.Utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping
public class FileController2 {

    private final FtpUtil ftpUtil;
    private final AsyncService asyncService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${file.local.root.path}")
    private String localFileRootPath;

    @Autowired
    public FileController2(FtpUtil ftpUtil, AsyncService asyncService) {
        this.ftpUtil = ftpUtil;
        this.asyncService = asyncService;
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String file) {
        try {
            InputStream inputStream = ftpUtil.downloadFile(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"")
                    .body(new InputStreamResource(inputStream));
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/merge")
    public Result mergeChunks(
            @RequestBody MergeRequest request) {
        String tempDir = DataUtil.getFilePath(localFileRootPath, request.getHash());
        Path mergedFile = Paths.get(tempDir, request.getFilename());
        try (OutputStream os = Files.newOutputStream(mergedFile)) {
            // 合并分块
            for (int i = 0; ; i++) {
                Path chunk = Paths.get(tempDir, String.valueOf(i));
                if (!Files.exists(chunk)) break;

                Files.copy(chunk, os);
                Files.delete(chunk); // 删除分块
            }
            String fileType = DataUtil.findLast(mergedFile.toString().split("\\."));
            String serverFilePath = "upload/" + DataUtil.getUUID() + "." + fileType;
            try (InputStream is = Files.newInputStream(mergedFile)) {
                CompletableFuture<Boolean> future = asyncService.uploadFtp(
                        serverFilePath, is);
                future.thenAccept(result -> {
                    if (result) {
                        logger.info("上传成功");
                        // 清理临时文件
                        try {
                            Files.delete(mergedFile);
                            Files.delete(Paths.get(tempDir));
                        } catch (IOException e) {
                            logger.error("清理临时文件出错", e);
                        }
                    } else {
                        logger.info("上传失败");
                    }
                });
//                ftpUtil.uploadFile(request.getFilename(), );
            }

            return Result.SUCCEED();
        } catch (Exception e) {
            logger.error("合并文件出错", e);
            return Result.FAILED();
        }
    }


    @GetMapping("/check")
    public Result CheckFile() {
        return Result.FAILED();
    }

    @PostMapping("/upload")
    public Result uploadChunk(
            @RequestParam("file") MultipartFile file,
            @RequestParam("hash") String hash,
            @RequestParam("index") int index) {

        String tempDir = DataUtil.getFilePath(localFileRootPath, hash);
        Path chunkPath = Paths.get(tempDir, String.valueOf(index));
        try {
            Files.createDirectories(chunkPath.getParent());
            file.transferTo(chunkPath);
            return Result.SUCCEED();
        } catch (IOException e) {
            logger.error("上传分块出错", e);
            return Result.FAILED();
        }
    }

}
