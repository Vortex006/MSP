package com.vortex.msp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource({"classpath:config/global.properties", "classpath:config/token.properties", "classpath:config/ftp" +
        ".properties", "classpath:config/file.properties"})
@EnableScheduling
@EnableCaching
public class MspApplication {

    public static void main(String[] args) {
        SpringApplication.run(MspApplication.class, args);
    }

}
