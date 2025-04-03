package me.vasujain.mfo_practical_13.controller;

import me.vasujain.mfo_practical_13.dto.ProductDto;
import me.vasujain.mfo_practical_13.dto.ReviewDto;
import me.vasujain.mfo_practical_13.response.ApiResponse;
import me.vasujain.mfo_practical_13.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a new product with associated categories
    @PostMapping
    public ApiResponse<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto created = productService.createProduct(productDto);
        return ApiResponse.<ProductDto>builder()
                .status(HttpStatus.CREATED)
                .message("Product created successfully")
                .data(created)
                .timestamp(LocalDate.now())
                .build();
    }

    // Add a review to a product
    @PostMapping("/{productId}/reviews")
    public ApiResponse<ProductDto> addReviewToProduct(@PathVariable UUID productId, @RequestBody ReviewDto reviewDto) {
        ProductDto productDto = productService.addReviewToProduct(productId, reviewDto);
        return ApiResponse.<ProductDto>builder()
                .status(HttpStatus.OK)
                .message("Review added to product successfully")
                .data(productDto)
                .timestamp(LocalDate.now())
                .build();
    }

    // Get product details (including categories and reviews)
    @GetMapping("/{productId}")
    public ApiResponse<ProductDto> getProduct(@PathVariable UUID productId) {
        ProductDto productDto = productService.getProductById(productId);
        return ApiResponse.<ProductDto>builder()
                .status(HttpStatus.OK)
                .message("Product retrieved successfully")
                .data(productDto)
                .timestamp(LocalDate.now())
                .build();
    }
}
