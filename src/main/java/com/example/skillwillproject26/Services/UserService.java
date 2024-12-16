package com.example.skillwillproject26.Services;

import com.example.skillwillproject26.Models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Date;
import java.time.Instant;

@Service
public class UserService {


    private String secretKey = "ksjkdfjlksdjlfkjskldfjklsjdlkfjksjdf";

    public String generateToken(String username, String password)
    {
        return Jwts.builder().signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .claim("username",username)
                .claim("role","User")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 + 60 * 1000))
                .compact();
    }

    private Claims getAllClaims(String token){
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build().parseSignedClaims(token);
        Claims payloads = claimsJws.getPayload();
        return payloads;
    }

    public Authentication authentication(String token)
    {
        Claims payloads = getAllClaims(token);
        String username = payloads.get("username",String.class);
        String role = payloads.get("role",String.class);

        return new UserModel(username,role);
    }

    public boolean isExpired(String token)
    {
        Claims allClaims = getAllClaims(token);
        Date expirationTime =  allClaims.getExpiration();
        return expirationTime.before(new Date(System.currentTimeMillis()));
    }



}
