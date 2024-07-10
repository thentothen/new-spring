package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping(value ="/login")
    public JSONObject login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("data", userService.login(user.getUsername(),user.getPassword(), request, response));
        return jsonObject;
    }
}
