package com.huang.config;

import com.huang.handler.MyAuthenticationFailureHandler;
import com.huang.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 设置别名
                .usernameParameter("username123")
                .passwordParameter("password123")
                // 当发现是/login时人为是登录，必须和表单的提交地址是一样的，去执行UserDetailsServiceImpl
                .loginProcessingUrl("/login")
                // 自定义登入页面
                .loginPage("/login.html")
                // 登录成功后跳转页面，Post请求
                // .successForwardUrl("/toMain")
                // 登录成功后处理器，不能和successForwardUrl共存
                .successHandler(new MyAuthenticationSuccessHandler("https://github.com/"))
                // 登录失败跳转的页面，Post请求
                // .failureForwardUrl("/toError");
                // 登录失败后处理器，failureForwardUrl共存
                .failureHandler(new MyAuthenticationFailureHandler("/error.html"));


        // 授权登入
        http.authorizeRequests()
                // login.html不需要认证
                .antMatchers("/login.html").permitAll()
                // error.html不需要认证
                .antMatchers("/error.html").permitAll()
                // 放行静态资源
                .antMatchers("/js/**","/css/**","/images/**").permitAll()
                // 所有后缀为.png都会被放行
                // .antMatchers("/**/*.png").permitAll()
                .regexMatchers("[.]png").permitAll()
                //单独放行POST,GET请求
                .regexMatchers(HttpMethod.GET,"/demo").permitAll()
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
