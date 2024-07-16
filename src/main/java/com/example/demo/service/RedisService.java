package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.utils.TokenUtil;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setList(String key, List<String> value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public List<String> getList(String key) {
        if(redisTemplate.opsForValue().get(key) == null) return new ArrayList<>();
        
        @SuppressWarnings({ "unchecked", "null" })
        List<String> list = ((Collection<String>) redisTemplate.opsForValue().get(key)).stream().filter(i->{
            try {
                TokenUtil.verifyToken(i);
                return true;
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
        this.setList(key, list);
        return list;
    }

    public Object get(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}
