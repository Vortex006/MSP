package com.zyj.msp;

import com.zyj.msp.Entity.Room;
import com.zyj.msp.Service.MenuService;
import com.zyj.msp.Service.RoomService;
import com.zyj.msp.ServiceImpl.MailService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MspApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MspApplicationTests.class);

    @Autowired
    private MailService mailService;

    @Test
    void contextLoads() {}


}
