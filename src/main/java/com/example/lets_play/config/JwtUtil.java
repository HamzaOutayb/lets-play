package com.example.lets_play.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET = "my-super-long-secret-key-which-is-secure-1234567890";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // ====== Generate token with userId and role ======
    public String generateToken(Long userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ====== Extract All Claims ======
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            return null;
        }
    }

    // ====== Extract userId ======
    public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);

        if (claims != null) {
            return claims.getSubject();
        }

        return null;
    }

    // ====== Extract role ======
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return (claims != null) ? (String) claims.get("role") : null;
    }

    // ====== Validate token ======
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims != null && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}