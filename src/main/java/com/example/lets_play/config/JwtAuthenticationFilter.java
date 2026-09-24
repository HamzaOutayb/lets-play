package com.example.lets_play.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.lets_play.dto.Userinfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.lets_play.security.JwtService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println("===== JWT FILTER =====");
        System.out.println("Request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("Authorization: " + request.getHeader("Authorization"));
        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {
                // 1. Validate token first
                if (jwtService.validateToken(token)) {
                    String userId = jwtService.extractUserId(token);
                    String role = jwtService.extractRole(token);

                    if (userId != null && role != null &&
                            SecurityContextHolder.getContext().getAuthentication() == null) {

                        Userinfo userInfo = new Userinfo(userId, role);

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userInfo,
                                null,
                                List.of(new SimpleGrantedAuthority(role)));

                        SecurityContextHolder.getContext()
                                .setAuthentication(authToken);
                    }
                }

            } catch (Exception e) {
                System.out.println("JWT authentication failed: " + e.getMessage());
            }
        }

        // Continue to controller
        filterChain.doFilter(request, response);
    }
}