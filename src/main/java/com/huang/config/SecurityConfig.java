package com.huang.config;

import com.huang.handler.MyAccessDeniedHandler;
import com.huang.handler.MyAuthenticationFailureHandler;
import com.huang.handler.MyAuthenticationSuccessHandler;
import com.huang.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;






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
                .successForwardUrl("/toMain")
                // 登录成功后处理器，不能和successForwardUrl共存
                // .successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                // 登录失败跳转的页面，Post请求
                .failureForwardUrl("/toError");
                // 登录失败后处理器，failureForwardUrl共存
                // .failureHandler(new MyAuthenticationFailureHandler("/error.html"));


        // 授权登入
        http.authorizeRequests()
                // login.html不需要认证
                .antMatchers("/login.html").permitAll()
                // .antMatchers("/login").access("permitAll()")
                // error.html不需要认证
                .antMatchers("/error.html").permitAll()
                // .antMatchers("/login.html").access("permitAll()")
                // 放行静态资源
                .antMatchers("/js/**","/css/**","/images/**").permitAll()
                // 所有后缀为.png都会被放行
                // .antMatchers("/**/*.png").permitAll()
                //正则表达式匹配
                // .regexMatchers("[.]png").permitAll()
                // .regexMatchers(HttpMethod.GET,"/demo").permitAll()
                // .mvcMatchers("/demo").servletPath("/huang").permitAll()
                // .antMatchers("/huang/demo").permitAll()
                // 设置指定一个权限才能跳转(严格区分大小写)
                // .antMatchers("/main1.html").hasAuthority("admin")
                // 设置指定多个权限能跳转
                // .antMatchers("main1.html").hasAnyAuthority("admin","abc")
                // 单个角色权限
                // .antMatchers("main1.html").hasRole("abc")
                // .antMatchers("/main1.html").access("hasRole(\"abc\")")
                // 多个角色权限
                // .antMatchers("main1.html").hasAnyRole("abc,cab")
                // ip判断
                // .antMatchers("/main1.html").hasIpAddress("127.0.0.1")
                // 所有请求都必须被认证，必须登入后才能认证
                .anyRequest().authenticated();
                // .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");
        // 关闭csrf防火墙
        http.csrf().disable();


        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        //记住我
        http.rememberMe()
                //自定义失效时间 ,单位秒
                .tokenValiditySeconds(60)

                // .rememberMeParameter()
                // 自定义登入逻辑
                .userDetailsService(userDetailsService)
                // 持久层对象
                .tokenRepository(persistentTokenRepository);
    }

    @Bean
    public PasswordEncoder getPw() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        // 自动建表，第一次启动的时候需要 第二次启动要注释掉

        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

}
