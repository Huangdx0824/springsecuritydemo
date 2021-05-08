package com.huang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @return
     */
    @RequestMapping("toMain")
    public String toMain(){
        return "redirect:main.html";
    }

    /**
     * 页面失败跳转
     * @return
     */
    @RequestMapping("toError")
    public String toError(){
        return "redirect:error.html";
    }
}



