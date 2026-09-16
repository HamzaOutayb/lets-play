package com.example.lets_play.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lets_play.dto.ProductRequest;
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
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);

    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }

    public Product createProduct(ProductRequest request) {

    Product product = new Product();

    product.setName(request.getName().trim());
    product.setDescription(request.getDescription().trim());
    product.setPrice(request.getPrice());

    return productRepository.save(product);
    }

}