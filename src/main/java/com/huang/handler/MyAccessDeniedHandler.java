package com.huang.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // response.sendRedirect();
        request.getRequestDispatcher("/403").forward(request, response);

        // // 设置相应的状态码
        // response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // response.setHeader("Content-Type","application/json;charset=utf-8");
        // PrintWriter writer = response.getWriter();
        // writer.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员\"}");
        // writer.flush();
        // writer.close();
    }
}
