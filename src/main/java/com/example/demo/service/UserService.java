package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.StaticVal;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

    public boolean login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        User user = userMapper.getUserInfo(username);

        if(generate(password).equals(user.getPassword())){
            jakarta.servlet.http.HttpSession session = request.getSession();
            session.setAttribute("username", username);
            try {
                String token = createToken(user.getId(), user.getUsername());
                System.out.println(token);
                
                List<String> tokenList = Optional.ofNullable(redisService.getList("Token")).orElse(new ArrayList<>())  ;

                tokenList.add(token);
                
                Cookie[] cookie = request.getCookies();
                for (int i = 0; i < cookie.length; i++) {
                    Cookie cook = cookie[i];
                    if (cook.getName().equals("Token")) {
                        tokenList= tokenList.stream()
                                            .filter(val->!val.equals(cook.getValue().toString()))
                                            .collect(Collectors.toList());
                    }
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

      public static String createToken(Long user_id, String user_name) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")
                .withClaim("type", "sso")
                .withClaim("user_id", user_id == null ? null : user_id.toString())
                .withClaim("user_name", user_name == null ? null : user_name.toString())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    @SuppressWarnings("null")
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

}
