package com.example.demo.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WsHandler extends TextWebSocketHandler {

    
    private static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public boolean isExisted(String id){
        for (WebSocketSession session : sessions) {
            if(session.getAttributes().get("userId").toString().equals(id)){
                System.out.println("isOpen"+session.isOpen());
                return true;
            }
        }
        return false;
    }
    @Override
    public void afterConnectionEstablished(@SuppressWarnings("null") WebSocketSession session) throws Exception {
        // 连接建立后的逻辑
        sessions.add(session);
        System.err.println("connect");
         // 从 WebSocketSession 中获取属性
        String userId = (String) session.getAttributes().get("userId");
        System.out.println(sessions); 
        // broadcast("有人来了");
    }

    @Override
    protected void handleTextMessage(@SuppressWarnings("null") WebSocketSession session, @SuppressWarnings("null") TextMessage message) throws Exception {
        // 处理消息的逻辑
        System.err.println("message"+message);
    }

    @Override
    public void afterConnectionClosed(@SuppressWarnings("null") WebSocketSession session, @SuppressWarnings("null") CloseStatus status) throws Exception {
        // 连接关闭后的逻辑
        System.err.println("close");
        sessions.remove(session);
    }

    public static void broadcast(String message) {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void sendMessageById(String id, String message){
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                if(session.getAttributes().get("userId").toString().equals(id)){
                    sendMessage(session,message);
                    System.out.println(session);
                    System.out.println("isOpen"+session.isOpen());
                }
            }
        }
    }
    public void sendMessage(WebSocketSession session, String message) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
