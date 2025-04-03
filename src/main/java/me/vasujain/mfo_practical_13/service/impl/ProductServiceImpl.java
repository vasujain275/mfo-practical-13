package me.vasujain.mfo_practical_13.service.impl;

import me.vasujain.mfo_practical_13.dto.CategoryDto;
import me.vasujain.mfo_practical_13.dto.ProductDto;
import me.vasujain.mfo_practical_13.dto.ReviewDto;
import me.vasujain.mfo_practical_13.model.Category;
import me.vasujain.mfo_practical_13.model.Product;
import me.vasujain.mfo_practical_13.model.Review;
import me.vasujain.mfo_practical_13.repository.ProductRepository;
import me.vasujain.mfo_practical_13.repository.ReviewRepository;
import me.vasujain.mfo_practical_13.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        // Map categories if provided
        if (productDto.getCategories() != null) {
            List<Category> categories = productDto.getCategories().stream().map(catDto -> {
                Category category = new Category();
                category.setCategoryName(catDto.getCategoryName());
                return category;
            }).collect(Collectors.toList());
            product.setCategories(categories);
        }
        product = productRepository.save(product);

        // Map to DTO
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());

        // Map reviews if any
        if (product.getReviews() != null) {
            dto.setReviews(product.getReviews().stream().map(review -> {
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setReviewId(review.getReviewId());
                reviewDto.setComment(review.getComment());
                reviewDto.setRating(review.getRating());
                return reviewDto;
            }).collect(Collectors.toList()));
        }
        // Map categories
        if (product.getCategories() != null) {
            List<CategoryDto> categoryDtos = product.getCategories().stream().map(cat -> {
                CategoryDto catDto = new CategoryDto();
                catDto.setCategoryId(cat.getCategoryId());
                catDto.setCategoryName(cat.getCategoryName());
                return catDto;
            }).collect(Collectors.toList());
            dto.setCategories(categoryDtos);
        }
        return dto;
    }

    @Override
    public ProductDto addReviewToProduct(UUID productId, ReviewDto reviewDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Review review = new Review();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setProduct(product);
        review = reviewRepository.save(review);
        // Add review to product
        product.getReviews().add(review);
        product = productRepository.save(product);
        return getProductById(product.getProductId());
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        if (product.getReviews() != null) {
            dto.setReviews(product.getReviews().stream().map(review -> {
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setReviewId(review.getReviewId());
                reviewDto.setComment(review.getComment());
                reviewDto.setRating(review.getRating());
                return reviewDto;
            }).collect(Collectors.toList()));
        }
        if (product.getCategories() != null) {
            List<CategoryDto> categoryDtos = product.getCategories().stream().map(cat -> {
                CategoryDto catDto = new CategoryDto();
                catDto.setCategoryId(cat.getCategoryId());
                catDto.setCategoryName(cat.getCategoryName());
                return catDto;
            }).collect(Collectors.toList());
            dto.setCategories(categoryDtos);
        }
        return dto;
    }
}
