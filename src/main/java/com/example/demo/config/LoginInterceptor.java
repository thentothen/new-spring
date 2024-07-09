package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpSession;

import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // 从请求头中获取 token
        String token =  (String) session.getAttribute("token");
        System.out.println("session-token: "+token);
        
        // 这里假设有一个方法 validateToken(token) 验证 token 是否有效
        if (token != null && validateToken(token)) {
            return true; // token 有效，继续处理请求
        }

        // token 无效，返回 401 未授权状态
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return true;
    }

    private boolean validateToken(String token) {
        // 这里应该实现实际的 token 验证逻辑，比如查询 Redis 或数据库
        return "validToken".equals(token); // 仅作为示例
    }
}
