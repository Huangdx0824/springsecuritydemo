package com.huang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 当发现是/login时人为是登录，必须和表单的提交地址是一样的，去执行UserDetailsServiceImpl
                .loginProcessingUrl("/login")
                // 自定义登入页面
                .loginPage("/login.html")
                // 登入成功后跳转页面，Post请求
                .successForwardUrl("/toMain")
                // 登录失败跳转的页面，Post请求
                .failureForwardUrl("/toError");



        // 授权登入
        http.authorizeRequests()
                // login.html不需要认证
                .antMatchers("/login.html").permitAll()
                // error.html不需要认证
                .antMatchers("/error.html").permitAll()
                // 所有请求都必须被认证，必须登入后才能认证
                .anyRequest().authenticated();

        // 关闭csrf防火墙
        http.csrf().disable();

    }

    @Bean
    public PasswordEncoder getPw() {
        return new BCryptPasswordEncoder();
    }

}
