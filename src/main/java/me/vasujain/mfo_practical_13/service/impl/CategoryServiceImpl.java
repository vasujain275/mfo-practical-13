package me.vasujain.mfo_practical_13.service.impl;

import me.vasujain.mfo_practical_13.dto.CategoryDto;
import me.vasujain.mfo_practical_13.dto.ProductDto;
import me.vasujain.mfo_practical_13.model.Category;
import me.vasujain.mfo_practical_13.repository.CategoryRepository;
import me.vasujain.mfo_practical_13.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        if(category.getProducts() != null) {
            dto.setProducts(category.getProducts().stream().map(product -> {
                ProductDto prodDto = new ProductDto();
                prodDto.setProductId(product.getProductId());
                prodDto.setProductName(product.getProductName());
                prodDto.setPrice(product.getPrice());
                return prodDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }
}
