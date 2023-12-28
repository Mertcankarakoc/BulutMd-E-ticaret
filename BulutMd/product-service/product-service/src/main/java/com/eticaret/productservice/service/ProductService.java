package com.eticaret.productservice.service;

import com.eticaret.productservice.model.Product;
import com.eticaret.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Transactional
    public void createProduct(Product product) {
        validateProduct(product);
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(long productId, Product updatedProduct) {
        validateProduct(updatedProduct);
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productRepository.delete(existingProduct);
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Optional<Product> find(long productId) {
        return productRepository.findById(productId);
    }

    @Transactional
    public void uploadImage(long productId, MultipartFile image) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        try {
            String uploadDir = "images";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFileName = image.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(image.getInputStream(), filePath);
            String imageUrl = "/images/" + uniqueFileName;
            existingProduct.setImageUrl(imageUrl);
            productRepository.save(existingProduct);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the image. Please try again.", e);
        }
    }

    public void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be null or negative");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be null or negative");
        }
    }
}