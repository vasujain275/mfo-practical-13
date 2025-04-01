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
