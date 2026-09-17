package com.example.lets_play.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lets_play.dto.ProductRequest;
import com.example.lets_play.exception.ResourceNotFoundException;
import com.example.lets_play.model.Product;
import com.example.lets_play.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = getProductById(id);

        if (product.getName() != null) {
            existingProduct.setName(product.getName().trim());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription().trim());
        }
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);
    }

    public Product updateProduct(String id, ProductRequest request) {
        Product existingProduct = getProductById(id);

        if (request.getName() != null) {
            existingProduct.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription().trim());
        }
        existingProduct.setPrice(request.getPrice());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        if (request.getName() != null) {
            product.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription().trim());
        }
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }
}