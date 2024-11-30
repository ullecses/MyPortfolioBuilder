package com.example.myportfoliobuilder.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String secretKey = "YourSecretKey";
    private final long expirationTime = 86400000; // Время жизни токена в миллисекундах (1 день)

    // Метод генерации токена
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Метод для извлечения email из токена
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Метод для проверки токена
    public boolean isTokenValid(String token, String email) {
        return (email.equals(extractEmail(token)) && !isTokenExpired(token));
    }

    // Проверка на истечение срока действия токена
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Извлечение Claims из токена
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}