package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Service.AsyncService;
import com.vortex.msp.Utils.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncServiceImpl implements AsyncService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FtpUtil ftpUtil;

    @Autowired
    public AsyncServiceImpl(FtpUtil ftpUtil) {
        this.ftpUtil = ftpUtil;
    }

    @Async("GlobalTaskExecutor")
    @Override
    public CompletableFuture<String> test() {
        CompletableFuture<String> future = new CompletableFuture<>();
        logger.info("任务开始执行");
        try {
            Thread.sleep(20000);
            future.complete("异步任务完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("任务执行结束");
        return future;
    }


    @Async("GlobalTaskExecutor")
    @Override
    public CompletableFuture<Boolean> uploadFtp(String serverFilePath, String localFilePath) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        logger.info("FTP上传文件任务开始执行");
        try {
            boolean isUpload = ftpUtil.uploadFile(serverFilePath, localFilePath);
            future.complete(isUpload);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("FTP上传文件任务执行结束");
        return future;
    }

    @Async("GlobalTaskExecutor")
    @Override
    public CompletableFuture<Boolean> uploadFtp(String serverFilePath, InputStream inputStream) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        logger.info("FTP上传文件任务开始执行");
        try {
            boolean isUpload = ftpUtil.uploadFile(serverFilePath, inputStream);
            future.complete(isUpload);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("FTP上传文件任务执行结束");
        return future;
    }

}
