package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {


     public static final String SECRET = "JKKLJOoasdlfj";
    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    public boolean login(String username, String password, HttpServletRequest request){
        User user = userMapper.getUserInfo(username);
        
        if(generate(password).equals(user.getPassword())){
            jakarta.servlet.http.HttpSession session = request.getSession();
            session.setAttribute("username", username);
            try {
                String token = createToken(user.getId());
                System.out.println(token);
                
                List<String> tokenList = redisService.getList("token");
                if(tokenList == null) tokenList = new ArrayList<String>();
                tokenList.add(token);
                
                redisService.setList("token",tokenList);

                session.setAttribute("token", token);
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

      public static String createToken(Long user_id) throws Exception {
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
                .withClaim("aud", "APP").withClaim("user_id", null == user_id ? null : user_id.toString())
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
