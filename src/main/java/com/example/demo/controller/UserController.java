package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.LoginData;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
public class UserController {
    @Autowired
    private UserService userService;
    

    @GetMapping("/getUser")
    public JSONObject getUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("data", userService.getUser( request, response));
        return jsonObject;
    }
    
    @PostMapping(value ="/login")
    public JSONObject login(@RequestBody LoginData user, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("data", userService.login(user.getUsername(),user.getPassword(), request, response));
        return jsonObject;
    }
    
    @GetMapping("/cleanToken")
    public JSONObject cleanToken() {
        userService.cleanToken();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", 1);
        return jsonObject;
    }
    
}
