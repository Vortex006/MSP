package com.vortex.msp.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Value("${spring.isTest}")
//    private boolean isTest;

    //这里是放行静态资源的
    @Override
    public void configure(WebSecurity web) throws Exception {
//        if (isTest) {
        web.ignoring().antMatchers("/**");
//        } else {
//            web.ignoring()
//                    .antMatchers("/resources/**",
//                            "/static/**", "/css/**",
//                            "/js/**", "/login/**", "/images/**", "/index.html");
//        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().loginPage("http://localhost:9090");
        http.formLogin().usernameParameter("userName");
        http.formLogin().passwordParameter("password");
        http.formLogin().loginProcessingUrl("/login/login");
    }

}