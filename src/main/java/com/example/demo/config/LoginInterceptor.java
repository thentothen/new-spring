package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.service.RedisService;
import com.example.demo.utils.StaticVal;
import com.example.demo.utils.TokenUtil;

import jakarta.servlet.http.Cookie;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        
        List<String> tokenList = redisService.getList("Token");
        
        Cookie[] cookie = request.getCookies();
        for (int i = 0; i < cookie.length; i++) {
            Cookie cook = cookie[i];
            if (cook.getName().equals("Token")) {
                Boolean inTokenList = tokenList.stream()
                                                .anyMatch(_i->_i.equals(cook.getValue().toString()));
                
                try {
                    Map<String, Claim> claims = TokenUtil.verifyToken(cook.getValue());

                    System.out.println(claims.get("user_id").asString());        
                    System.out.println(claims.get("user_name").asString());  
                    
                    if (inTokenList) {
                        return true; 
                    }      
                } catch (Exception e) {

                }
                
               
            }
        }

        // token 无效，返回 401 未授权状态
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

}
