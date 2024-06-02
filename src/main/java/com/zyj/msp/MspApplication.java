package com.zyj.msp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:config/TokenConfig.properties"})
public class MspApplication {

    public static void main(String[] args) {
        SpringApplication.run(MspApplication.class, args);
    }

}
