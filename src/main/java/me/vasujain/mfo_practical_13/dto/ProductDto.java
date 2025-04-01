package me.vasujain.mfo_practical_13.dto;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID productId;
    private String productName;
    private Double price;
    private List<ReviewDto> reviews;
}