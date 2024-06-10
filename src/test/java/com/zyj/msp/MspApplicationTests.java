package com.zyj.msp;

import com.zyj.msp.Service.RedisService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MspApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MspApplicationTests.class);

    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        Boolean aBoolean = redisService.setString("name", "1");
        Long name = redisService.valueAdd("name", 11);
        logger.info("{}", name);
    }


}
