package com.fintrackerapi.fintracker.components;

import com.fintrackerapi.fintracker.dtos.CategoryDto;
import com.fintrackerapi.fintracker.entities.Category;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CategoryConverter {

    private CategoryConverter() {}

    public static Category convertToCategoryEntity(CategoryDto categoryDto) {
        if (categoryDto == null) throw new RuntimeException("CategoryDto can not be null");

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setAmountAllocated(categoryDto.getAmountAllocated());
        category.setAmountSpent(BigDecimal.ZERO);
        category.setDueDate(categoryDto.getDueDate());

        return category;
    }

    public static CategoryResponse convertToCategoryResponse(Category category) {
        if (category == null) throw new RuntimeException("Category can not be null");

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getAmountAllocated(),
                category.getAmountSpent(),
                category.getDueDate(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
