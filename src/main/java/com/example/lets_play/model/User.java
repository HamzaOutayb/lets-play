package com.example.lets_play.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Document(collection = "User")
public class User {

    @Id
    private String id;

    @Field("name")
    @NotBlank
    @Size(max = 100)
    private String name;

    @Field("email")
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @Field("password")
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @Field("role")
    private Role role;

    public User() {
    }

    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}