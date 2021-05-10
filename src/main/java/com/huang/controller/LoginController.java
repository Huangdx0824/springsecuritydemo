package com.huang.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    // /**
    //  * 登录
    //  * @return
    //  */
    // @RequestMapping("login")
    // public String login() {
    //     System.out.println("执行登入方法");
    //     return "redirect:main.html";
    // }

    /**
     * 页面成功跳转
     *
     * @return
     */
    // @Secured("ROLE_abc") //开启权限abc
    @PreAuthorize("hasRole('abc')") //PreAuthorize 的表达式允许ROLE_开头，也可以不以ROLE_开头，配置类允许ROLE_开头
    @RequestMapping("toMain")
    public String toMain() {
        return "redirect:main.html";
    }

    /**
     * 页面失败跳转
     *
     * @return
     */
    @RequestMapping("toError")
    public String toError() {
        return "redirect:error.html";
    }

    @GetMapping("demo")
    @ResponseBody
    public String Demo() {
        return "demo";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping("/403")
    @ResponseBody
    public Map<String, String> forbidden() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "error");
        map.put("msg", "权限不足，请联系管理员");
        return map;
    }
}
