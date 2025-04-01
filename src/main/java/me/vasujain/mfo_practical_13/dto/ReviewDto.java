package me.vasujain.mfo_practical_13.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewDto {
    private UUID reviewId;
    private String comment;
    private int rating;
    private UUID productId;
}