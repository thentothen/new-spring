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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static final String SECRET = "JKKLJOoasdlfj";
    
    @Autowired
    private RedisService redisService;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession();

        List<String> tokenList = redisService.getList("Token");
        System.out.println(tokenList);
        
        
        Cookie[] cookie = request.getCookies();
        for (int i = 0; i < cookie.length; i++) {
            Cookie cook = cookie[i];
            if (cook.getName().equals("Token")) {
                String inTokenList = tokenList.stream().filter(_i->_i==cook.getValue().toString()).findFirst().toString();

                try {
                    Map<String, Claim> claims = verifyToken(cook.getValue());

                    System.out.println(claims.get("user_id").asString());        
                    System.out.println(claims.get("user_name").asString());  
                    
                    if (inTokenList != null) {
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

    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
            System.out.println("start:  "+jwt.getIssuedAt());
            System.out.println("end: "+jwt.getExpiresAt());
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
            //token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

}
