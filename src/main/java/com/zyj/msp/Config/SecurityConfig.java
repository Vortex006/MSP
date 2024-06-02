package com.zyj.msp.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //这里是放行静态资源的
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/resources/**",
//                        "/static/**", "/css/**",
//                        "/js/**", "/images/**", "/login/**", "/index.html");

        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/index.html");
        http.formLogin().usernameParameter("userName");
        http.formLogin().passwordParameter("password");
        http.formLogin().loginProcessingUrl("/login/login");
    }

}