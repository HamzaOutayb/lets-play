package com.example.lets_play.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lets_play.dto.ProductRequest;
import com.example.lets_play.exception.ForbiddenException;
import com.example.lets_play.exception.ResourceNotFoundException;
import com.example.lets_play.model.Product;
import com.example.lets_play.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        ));
    }

    public Product updateProduct(
            String id,
            Product product,
            String userId,
            String role) {

        Product existingProduct = getProductById(id);

        boolean isOwner =
                Objects.equals(existingProduct.getUserId(), userId);

        boolean isAdmin =
                "ADMIN".equals(role);

        if (!isOwner && !isAdmin) {
            throw new ForbiddenException(
                    "You are not authorized to update this product."
            );
        }

        if (product.getName() != null) {
            existingProduct.setName(
                    product.getName().trim()
            );
        }

        if (product.getDescription() != null) {
            existingProduct.setDescription(
                    product.getDescription().trim()
            );
        }

        if (product.getPrice() >= 0) {
            existingProduct.setPrice(product.getPrice());
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(
            String id,
            String userId,
            String role) {

        Product existingProduct = getProductById(id);

        boolean isOwner =
                Objects.equals(existingProduct.getUserId(), userId);

        boolean isAdmin =
                "ADMIN".equals(role);

        if (!isOwner && !isAdmin) {
            throw new ForbiddenException(
                    "You are not authorized to delete this product."
            );
        }

        productRepository.delete(existingProduct);
    }

    public Product createProduct(
            ProductRequest request,
            String userId) {

        Product product = new Product();

        if (request.getName() != null) {
            product.setName(
                    request.getName().trim()
            );
        }

        if (request.getDescription() != null) {
            product.setDescription(
                    request.getDescription().trim()
            );
        }

        product.setUserId(userId);
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }
}