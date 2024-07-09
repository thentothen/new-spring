package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.User;

public interface UserMapper {
    
    User getUserInfo(@Param("username") String username);

}
