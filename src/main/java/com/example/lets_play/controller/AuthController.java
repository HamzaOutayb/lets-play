package com.example.lets_play.controller;

import com.example.lets_play.dto.AuthResponse;
import com.example.lets_play.dto.Registe;
import com.example.lets_play.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Auths")
public class AuthController {
    private final AuthService AuthService;

    public AuthController(AuthService AuthService) {
        this.AuthService = AuthService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse createUser(@Valid @RequestBody Registe Auth) {
        return AuthService.createUser(Auth);
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