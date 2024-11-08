package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncServiceImpl implements AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

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

}
