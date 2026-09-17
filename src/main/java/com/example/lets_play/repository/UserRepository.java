package com.example.lets_play.repository;

import com.example.lets_play.model.Product;
import com.example.lets_play.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Product, String> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User save(User user);
}
