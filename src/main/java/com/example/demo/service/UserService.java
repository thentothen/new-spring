package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.auth0.jwt.interfaces.Claim;
import com.example.demo.entity.LoginData;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.StaticVal;
import com.example.demo.utils.TokenUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {


    public static final String SECRET = StaticVal.SECRET;
    
    /** token 过期时间: 24小时 */
    public static final int calendarField = Calendar.HOUR;
    public static final int calendarInterval = 24;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    public void cleanToken(){
        redisService.setList("Token", new ArrayList<>());
    }

    public User getUser(HttpServletRequest request, HttpServletResponse response){
        Long userId = (long) -1;
        Cookie[] cookie = request.getCookies();
                for (int i = 0; i < cookie.length; i++) {
                    Cookie cook = cookie[i];
                    if (cook.getName().equals("Token")) {

                        Map<String, Claim> claims = TokenUtil.verifyToken(cook.getValue());
                        userId = Long.valueOf(claims.get("user_id").asString());
                    }
                }

        User user = userMapper.getUserById(userId);
        return user;
    }

    public boolean login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        LoginData user = userMapper.getLoginData(username);

        if(generate(password).equals(user.getPassword())){
            jakarta.servlet.http.HttpSession session = request.getSession();
            session.setAttribute("username", username);
            try {
                String token = TokenUtil.createToken(user.getId(), user.getUsername());
                System.out.println(token);
                
                List<String> tokenList = Optional.ofNullable(redisService.getList("Token")).orElse(new ArrayList<>());

                tokenList.add(token);
                
                String cookieToken = CookieUtil.getCookie(request.getCookies(), "Token");
                if(!cookieToken.isEmpty()){
                    tokenList = tokenList.stream()
                                            .filter(val->!val.equals(cookieToken))
                                            .collect(Collectors.toList());
                }

                redisService.setList("Token",tokenList);
                
                session.setAttribute("Token", token);
                
                Cookie cookie1 = new Cookie("Token", token);
                cookie1.setPath("/");
                response.addCookie(cookie1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return generate(password).equals(user.getPassword()); 
    }

    public String generate(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            String result = new BigInteger(1, digest).toString(16).toUpperCase();
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "err";
        }
        
    }
}
