package com.vortex.msp.Task;

import com.vortex.msp.Service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisService redisService;

    @Autowired
    public DemoTask(RedisService redisService) {
        this.redisService = redisService;
    }

    //    @Scheduled(cron = "*/5 * * * * ?")
    public void test() {
        logger.debug("输入成功");
    }
}
