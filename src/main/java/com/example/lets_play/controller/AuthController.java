package com.example.lets_play.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_play.dto.AuthResponse;
import com.example.lets_play.dto.Loginrequest;
import com.example.lets_play.dto.Registe;
import com.example.lets_play.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/Auths")
public class AuthController {
    private final AuthService AuthService;

    public AuthController(AuthService AuthService) {
        this.AuthService = AuthService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse createUser(@Valid @RequestBody Registe Auth) {
        return AuthService.createUser(Auth);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody Loginrequest Auth) {
        return AuthService.login(Auth);
    }

    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public List<Auth> getAllAuths() {
    //     return AuthService.getAllAuths();
    // }

    // @GetMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public Auth getAuthById(@PathVariable String id) {
    //     return AuthService.getAuthById(id);
    // }

    // @PutMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public Auth updateAuth(@PathVariable String id, @RequestBody Auth Auth) {
    //     return AuthService.updateAuth(id, Auth);
    // }

    // @DeleteMapping("/{id}")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // public void deleteAuth(@PathVariable String id) {
    //     AuthService.deleteAuth(id);
    // }
}