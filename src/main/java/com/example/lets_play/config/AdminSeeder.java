package com.example.lets_play.config;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.lets_play.model.Role;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.UserRepository;

@Component
public class AdminSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        try {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                User admin = new User();
                admin.setRole(Role.ADMIN);
                admin.setName("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123456789"));
                userRepository.save(admin);
                System.out.println(" Admin user created successfully!");
            } else {
                System.out.println(" Admin user already exists, skipping creation.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" Failed to create admin: " + e.getMessage());
        }
    }

}