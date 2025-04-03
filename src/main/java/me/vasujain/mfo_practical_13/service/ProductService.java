package me.vasujain.mfo_practical_13.service;

import me.vasujain.mfo_practical_13.dto.ProductDto;
import me.vasujain.mfo_practical_13.dto.ReviewDto;

import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto addReviewToProduct(UUID productId, ReviewDto reviewDto);
    ProductDto getProductById(UUID productId);
}