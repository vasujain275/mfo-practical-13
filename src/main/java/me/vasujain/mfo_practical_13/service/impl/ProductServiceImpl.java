package me.vasujain.mfo_practical_13.service.impl;

import me.vasujain.mfo_practical_13.dto.ProductDto;
import me.vasujain.mfo_practical_13.dto.ReviewDto;
import me.vasujain.mfo_practical_13.model.Product;
import me.vasujain.mfo_practical_13.model.Review;
import me.vasujain.mfo_practical_13.repository.ProductRepository;
import me.vasujain.mfo_practical_13.repository.ReviewRepository;
import me.vasujain.mfo_practical_13.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.UUID;

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
    public ProductDto addReviewToProduct(UUID productId, ReviewDto reviewDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Review review = new Review();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setProduct(product);
        review = reviewRepository.save(review);
        // Update product reviews list (if needed, otherwise lazy loading works too)
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
        if(product.getReviews() != null) {
            dto.setReviews(product.getReviews().stream().map(review -> {
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setReviewId(review.getReviewId());
                reviewDto.setComment(review.getComment());
                reviewDto.setRating(review.getRating());
                reviewDto.setProductId(product.getProductId());
                return reviewDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }
}
