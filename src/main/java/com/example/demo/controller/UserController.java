package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/login.html")
    public String greeting(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();

        System.out.println(user.toString());
        
        jsonObject.put("data",userService.login(user.getUsername(),user.getPassword(), request));
        
        return jsonObject;
    }
}
