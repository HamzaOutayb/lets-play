package com.example.lets_play.controller;

import com.example.lets_play.dto.ProductRequest;
import com.example.lets_play.dto.Userinfo;
import com.example.lets_play.model.Product;
import com.example.lets_play.service.ProductService;

import java.util.List;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody ProductRequest product, Authentication authentication) {
        Userinfo userInfo = (Userinfo) authentication.getPrincipal();

        String userId = userInfo.getUserId();
        return productService.createProduct(product, userId);
    }

    @PermitAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PermitAll
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @PermitAll
    @GetMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByIdForAdminProduct(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable String id, @RequestBody Product product, Authentication authentication) {
        Userinfo userInfo = (Userinfo) authentication.getPrincipal();

        String userId = userInfo.getUserId();
        String Role = userInfo.getRole();

        return productService.updateProduct(id, product, userId, Role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id, Authentication authentication) {
        Userinfo userInfo = (Userinfo) authentication.getPrincipal();

        String userId = userInfo.getUserId();
        String Role = userInfo.getRole();
        productService.deleteProduct(id, userId, Role);
    }
}