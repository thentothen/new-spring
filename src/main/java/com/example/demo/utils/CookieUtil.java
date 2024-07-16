package com.example.demo.utils;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
    
    public static final String getCookie(Cookie[] cookies, String key){
        for (int i = 0; i < cookies.length; i++) {
            Cookie cook = cookies[i];
            if (cook.getName().equals(key)) {
                return cook.getValue().toString();
            }
        }
        return "";
    }
}
