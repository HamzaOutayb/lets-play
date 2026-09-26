package com.example.lets_play.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("price")
    private double price;

    @Field("userId")
    private String UserId;

    public Product() {
    }

    public Product(String name, String description, double price, String UserId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.UserId = UserId;
    }
}