package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Area;
import com.example.demo.mapper.ServiceAreaMapper;

@Service
public class ServiceAreaImp implements ServiceArea {

    @Autowired
    private ServiceAreaMapper ServiceAreaMapper;

    @Override
    public List<Area> getList(){
        return ServiceAreaMapper.getAreaList();
    }
}       
