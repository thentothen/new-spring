package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.websocket.*;

@RestController
public class WsController {
    
    private final WsHandler webSocketHandler = new WsHandler();

     @GetMapping("/ws/send/{sessionId}/{message}")
     public void sendMessageToClient(@PathVariable String sessionId, @PathVariable String message) {
        webSocketHandler.sendMessageById(sessionId, message);
    }

}
