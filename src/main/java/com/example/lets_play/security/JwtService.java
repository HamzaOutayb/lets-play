package com.example.lets_play.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.lets_play.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "my-super-secret-key-that-is-at-least-32-characters-long";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hour

    private final Key key = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes()
    );

    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getId())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(
                    new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key)
                .compact();
    }
}