package com.zyj.msp.ServiceImpl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TestServiceImpl {

    @Async
    public CompletableFuture<String> test() {
        CompletableFuture<String> future = new CompletableFuture<>();
        System.out.println("任务开始执行");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        future.complete("异步任务完成");
        System.out.println("任务执行结束");
        return future;
    }
}
