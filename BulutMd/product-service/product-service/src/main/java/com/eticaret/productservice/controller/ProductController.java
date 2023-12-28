package com.eticaret.productservice.controller;

import com.eticaret.productservice.model.Product;
import com.eticaret.productservice.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Api(value = "Product Management", description = "Operations pertaining to product in E-commerce System")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Create a new product", response = String.class)
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try {
            productService.validateProduct(product);
            productService.createProduct(product);
            return new ResponseEntity<>("The product was created successfully.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update an existing product", response = String.class)
    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable long productId, @RequestBody Product product) {
        try {
            productService.validateProduct(product);
            productService.updateProduct(productId, product);
            return new ResponseEntity<>("The product was updated successfully.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete a product", response = String.class)
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("The product was deleted successfully.", HttpStatus.OK);
    }

    @ApiOperation(value = "Get a specific product", response = Product.class)
    @GetMapping("/find/{productId}")
    public Optional<Product> getProduct(@PathVariable long productId) {
        return productService.find(productId);
    }

    @ApiOperation(value = "List all products", response = List.class)
    @GetMapping("/list")
    public List<Product> listProducts() {
        return productService.listAll();
    }

    @ApiOperation(value = "Upload an image for a product", response = String.class)
    @PostMapping("/image/{productId}")
    public ResponseEntity<String> uploadImage(@PathVariable long productId, @RequestParam("image") MultipartFile image) {
        try {
            productService.uploadImage(productId, image);
            return new ResponseEntity<>("The product's image was uploaded successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
