package com.example.demo.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {
    
    public static final String SECRET = StaticVal.SECRET;
    
    /** token 过期时间: 24小时 */
    public static final int calendarField = Calendar.HOUR;
    public static final int calendarInterval = 24;

    public static final String createToken(Long user_id, String user_name) throws Exception {
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
    
    public static final Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(StaticVal.SECRET)).build();
            jwt = verifier.verify(token);
            // System.out.println("start:  "+jwt.getIssuedAt());
            // System.out.println("end: "+jwt.getExpiresAt());
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
            //token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }
}
