package com.example.lets_play.dto;

import com.example.lets_play.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class AuthResponse {
    private String token;
    private String name;
    private Role role;
}
