package me.vasujain.mfo_practical_13.controller;

import me.vasujain.mfo_practical_13.dto.CategoryDto;
import me.vasujain.mfo_practical_13.response.ApiResponse;
import me.vasujain.mfo_practical_13.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get a category with all its products
    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryDto> getCategory(@PathVariable Integer categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return ApiResponse.<CategoryDto>builder()
                .status(HttpStatus.OK)
                .message("Category retrieved successfully")
                .data(categoryDto)
                .timestamp(LocalDate.now())
                .build();
    }
}
