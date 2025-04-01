package me.vasujain.mfo_practical_13.controller;

import me.vasujain.mfo_practical_13.dto.ReviewDto;
import me.vasujain.mfo_practical_13.response.ApiResponse;
import me.vasujain.mfo_practical_13.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewDto> getReview(@PathVariable UUID reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(reviewId);
        return ApiResponse.<ReviewDto>builder()
                .status(HttpStatus.OK)
                .message("Review retrieved successfully")
                .data(reviewDto)
                .timestamp(LocalDate.now())
                .build();
    }
}
