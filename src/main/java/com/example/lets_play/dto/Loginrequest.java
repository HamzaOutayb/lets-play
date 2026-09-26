package com.example.lets_play.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Loginrequest {
    @NotNull
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
}