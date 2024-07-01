package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ServiceArea;


@RestController
public class ServiceAreaController {
    
    @Autowired
    private ServiceArea serviceArea;

    @RequestMapping("/test")
    public List getList(){
        return serviceArea.getList();
    }

}
