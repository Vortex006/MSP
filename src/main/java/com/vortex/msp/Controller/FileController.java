package com.vortex.msp.Controller;

import com.vortex.msp.Utils.DataUtil;
import com.vortex.msp.Utils.FtpUtil;
import com.vortex.msp.Utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.download.max.size}")
    private int downloadMaxSize;
    @Value("${file.local.root.path}")
    private String localFileRootPath;

    @Autowired
    private FtpUtil ftpUtil;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<StreamingResponseBody> download(@PathVariable("fileName") String fileName) {
        try {
            String localPath = DataUtil.getFilePath(localFileRootPath, "file", fileName);
            File localFile = new File(localPath);
            File patient = localFile.getParentFile();
            if (!patient.exists()) {
                patient.mkdirs();
            }
            if (localFile.exists()) {
                localFile.delete();
            }

//            boolean isDownload = ftpUtil.downloadFile(fileName,localPath);
//            if(!isDownload){
//                throw new RuntimeException("从远程服务器下载文件失败");
//            }
//            File file = new File(localPath);
//            if(!file.exists()){
//                throw new RuntimeException("文件不存在");
//            }

            // 设置响应头信息
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(ftpUtil.getFileLength(fileName)));
            headers.add(HttpHeaders.CACHE_CONTROL, "no-store, must-revalidate"); // 防止缓存
            headers.add(HttpHeaders.PRAGMA, "no-cache"); // 防止缓存
            StreamingResponseBody body = outputStream -> {
                ftpUtil.downloadFile(fileName, outputStream);
                outputStream.flush();
//                try (FileInputStream inputStream = new FileInputStream(file)) {
//                    byte[] buffer = new byte[downloadMaxSize * 1024 * 1024];
//                    int bytesRead;
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                        outputStream.flush(); // 确保数据及时发送
//                    }
//                }
//                } catch (IOException e) {
//                    logger.error("文件转成数据流异常,异常信息==>{}", e.getMessage());
//                    throw new RuntimeException("文件转成数据流异常", e);
//                }
            };
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(body);
        } catch (Exception e) {
            StreamingResponseBody bodys = outputStream -> {
                String msg = "下载文件出现异常，异常信息==>" + e.getClass() + e.getMessage();
                outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
            };
            logger.error("下载文件出现异常,异常信息==>{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodys);
        }
    }

    @PostMapping("/upload")
    public Result uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName,
            @RequestParam("totalChunks") int totalChunks,
            @RequestParam("currentChunk") int currentChunk) throws IOException {
        String hz = fileName.substring(fileName.lastIndexOf("."));
        logger.debug("文件后缀名==>{}", hz);
        String newFileName = DataUtil.getUUID() + hz;
        logger.debug("新文件名==>{}", newFileName);
        String path = DataUtil.getFilePath(localFileRootPath, "file", newFileName);
        File targetFile = new File(path);
        File parentFile = targetFile.getParentFile();
        if (!parentFile.exists()) {
            boolean isMkdirs = parentFile.mkdirs();
            if (!isMkdirs) return Result.Exception("创建目录失败");
        }

        if (targetFile.exists() && currentChunk == 1) {
            boolean isDel = targetFile.delete();
            if (!isDel) return Result.Exception("删除文件失败");
        }

        try (OutputStream os = new FileOutputStream(targetFile, true)) {
            os.write(file.getBytes());
        }

        if (currentChunk == totalChunks) {
            logger.info("文件[{}]上传完毕", fileName);
            String ftpPath = fileName;
            logger.info("文件上传FTP路径==>{}", ftpPath);
            boolean isUpload = ftpUtil.uploadFile(ftpPath, targetFile.getAbsolutePath());
            if (isUpload) {
                logger.info("文件上传FTP成功");
            } else {
                logger.error("文件上传FTP失败，文件路径==>{}", targetFile.getAbsolutePath());
            }
            return Result.SUCCEED("文件已经上传成功", newFileName);
        }

        logger.info("文件[{}]分块上传成功，当前块：{}，总块数：{}", fileName, currentChunk, totalChunks);
        return Result.SUCCEED("分块上传成功", null);
    }

}
