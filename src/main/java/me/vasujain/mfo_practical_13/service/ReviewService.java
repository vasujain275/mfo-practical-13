package me.vasujain.mfo_practical_13.service;


import me.vasujain.mfo_practical_13.dto.ReviewDto;

import java.util.UUID;

public interface ReviewService {
    ReviewDto getReviewById(UUID reviewId);
}