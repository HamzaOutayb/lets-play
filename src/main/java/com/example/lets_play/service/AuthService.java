package com.example.lets_play.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.lets_play.dto.AuthResponse;
import com.example.lets_play.dto.Loginrequest;
import com.example.lets_play.dto.Registe;
import com.example.lets_play.model.Role;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.UserRepository;
import com.example.lets_play.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse createUser(Registe request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if username already exists
        if (userRepository.existsByname(request.getName())) {
            throw new RuntimeException("Username already exists");
        }

        // Create user
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Hash + salt password
        user.setPassword(
            passwordEncoder.encode(request.getPassword())
        );

        // New registrations are always USER
        user.setRole(Role.USER);

        // Save user
        User savedUser = userRepository.save(user);

        // Generate JWT
        String token = jwtService.generateToken(savedUser);

        // Return safe response
        return new AuthResponse(
            token,
            savedUser.getName(),
            savedUser.getRole()
        );
    }

    public AuthResponse login(Loginrequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getName(),
                user.getRole()
        );
    }
}