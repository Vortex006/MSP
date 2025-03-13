package com.vortex.msp.Config;

import com.vortex.msp.Interceptor.CodeInterceptor;
import com.vortex.msp.Interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MspWebConfigurer implements WebMvcConfigurer {

    private final JWTInterceptor jwtInterceptor;
    private final CodeInterceptor codeInterceptor;

//    @Value("${application.test}")
//    private boolean isTest;

    @Autowired
    public MspWebConfigurer(JWTInterceptor jwtInterceptor, CodeInterceptor codeInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
        this.codeInterceptor = codeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
/*        registry.addInterceptor(codeInterceptor);
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/login/**", "/index.html");*/

//        registry.excludePathPatterns("/login/**", "/index.html","/**");
//
//        if (isTest) {
//            registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/login/**")
//            .excludePathPatterns("/menu/**").excludePathPatterns("/scanQr").excludePathPatterns
//            ("/scanQr/status/**","/file/**");
//        } else {
//            registry.addInterceptor(jwtInterceptor)
//                    .addPathPatterns("/**").excludePathPatterns("/login/**", "/index.html");
//        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}