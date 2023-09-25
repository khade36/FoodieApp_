package com.user.user.service;


import com.user.user.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityJsonWebToken {
    public String  generateToken(User user) {
        Map<String, String> map = new HashMap<>();
        String JwtToken;
        JwtToken = Jwts.builder()
                .setSubject(user.getEmailId())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "Sushant").compact();
        map.put("Token", JwtToken);
//        map.put("Message", "Login is Successfully Done.");
        return JwtToken;
    }
}
