package com.vortex.msp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MspWebConfigurer implements WebMvcConfigurer {

    private final JWTInterceptor jwtInterceptor;

    @Value("${spring.isTest}")
    private boolean isTest;

    @Autowired
    public MspWebConfigurer(JWTInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (isTest) {
            registry.addInterceptor(jwtInterceptor)
                    .excludePathPatterns("/**");
        } else {
            registry.addInterceptor(jwtInterceptor)
                    .addPathPatterns("/**").excludePathPatterns("/login/**", "/index.html");
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}