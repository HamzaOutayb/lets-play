package com.example.lets_play.repository;

import com.example.lets_play.model.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByname(String name);

    User save(User user);

    Optional<User> findByEmail(String email);
}
