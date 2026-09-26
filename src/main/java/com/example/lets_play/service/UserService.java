package com.example.lets_play.service;

import com.example.lets_play.dto.UserInfo;
import com.example.lets_play.dto.UserResponse;
import com.example.lets_play.exception.ResourceNotFoundException;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserInfo request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        return userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole() != null
                                ? user.getRole().name()
                                : null
                ))
                .toList();
    }

    public UserResponse getUserByIdWithoutPassword(String id) {

        User user = getUserById(id);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole() != null
                        ? user.getRole().name()
                        : null
        );
    }

    public User getUserById(String id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        ));
    }

    public User updateUser(
            String id,
            User updatedUser) {

        User existingUser = getUserById(id);

        if (updatedUser.getName() != null &&
                !updatedUser.getName().isBlank()) {

            existingUser.setName(
                    updatedUser.getName().trim()
            );
        }

        if (updatedUser.getEmail() != null &&
                !updatedUser.getEmail().isBlank()) {

            existingUser.setEmail(
                    updatedUser.getEmail().trim().toLowerCase()
            );
        }

        if (updatedUser.getPassword() != null &&
                !updatedUser.getPassword().isBlank()) {

            existingUser.setPassword(
                    passwordEncoder.encode(
                            updatedUser.getPassword()
                    )
            );
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {

        User existingUser = getUserById(id);

        userRepository.delete(existingUser);
    }
}