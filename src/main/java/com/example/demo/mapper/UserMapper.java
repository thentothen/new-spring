package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.LoginData;
import com.example.demo.entity.User;

public interface UserMapper {
    
    LoginData getLoginData(@Param("username") String username);
    User getUserById(@Param("userId") Long userId);

}
