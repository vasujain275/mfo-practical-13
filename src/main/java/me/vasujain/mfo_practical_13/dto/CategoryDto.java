package me.vasujain.mfo_practical_13.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDto {
    private Integer categoryId;
    private String categoryName;
    private List<ProductDto> products;
}
