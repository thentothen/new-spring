package com.example.demo.websocket;

import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHandshakeInterceptor implements HandshakeInterceptor {

    private final WsHandler webSocketHandler = new WsHandler();

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 提取查询参数或头信息
        // String token = request.getParameter("token");
        // String userId = request.getParameter("userId");
        System.out.println("request "+request.getURI().toString());
        try {
            URL url = new URL(request.getURI().toString());
            String paramString = url.getQuery();
            for (String str : paramString.split("[&]")) {
                attributes.put(str.split("=")[0], str.split("=")[1]);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userId = attributes.get("userId").toString();

        
        // 验证 token 和 userId，或进行其他逻辑处理
        if (webSocketHandler.isExisted(userId) ) {
            System.out.println("已有连接");
            return false;
        }
        if (userId == null) {
            // response.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        // 返回 true 以继续握手，返回 false 以中断握手
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 在握手之后的逻辑
        System.out.println("Handshake complete");
    }
}
