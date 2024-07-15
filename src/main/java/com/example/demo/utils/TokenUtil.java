package com.example.demo.utils;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {
    
     public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(StaticVal.SECRET)).build();
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
