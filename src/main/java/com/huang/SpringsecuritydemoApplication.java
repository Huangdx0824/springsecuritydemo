package com.huang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableGlobalMethodSecurity(securedEnabled = true) //开启SpringSecurity访问控制的注解
@SpringBootApplication
public class SpringsecuritydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecuritydemoApplication.class, args);
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("a yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间

        System.out.println("更新完成时间" + sdf.format(date));
        System.out.println("http://localhost:8080/login.html");

    }

}
