package com.example.lets_play.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class Registe {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

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