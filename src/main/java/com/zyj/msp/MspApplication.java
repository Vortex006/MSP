package com.zyj.msp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource({"classpath:config/config.properties"})
@EnableScheduling
@EnableAsync
public class MspApplication {

    public static void main(String[] args) {
        SpringApplication.run(MspApplication.class, args);
    }

}
