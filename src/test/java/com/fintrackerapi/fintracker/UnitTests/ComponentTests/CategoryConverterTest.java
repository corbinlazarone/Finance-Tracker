package com.fintrackerapi.fintracker.UnitTests.ComponentTests;

import com.fintrackerapi.fintracker.components.CategoryConverter;
import com.fintrackerapi.fintracker.dtos.CategoryDto;
import com.fintrackerapi.fintracker.entities.Category;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST CASES
 * - Test convert category dto to category entity successfully
 * - Test convert category dto to category entity failure
 * - Test convert category entity to category response successfully
 * - Test convert category entity to category response failure
 */

@ExtendWith(MockitoExtension.class)
public class CategoryConverterTest {

    @Test
    public void convertToCategoryEntitySuccess() {

        CategoryDto category = new CategoryDto();
        category.setName("Test category name");
        category.setDescription("Test category description");
        category.setAmountAllocated(BigDecimal.valueOf(1500));
        category.setDueDate(LocalDate.now());

        Category convertedCategory = CategoryConverter.convertToCategoryEntity(category);

        assertNotNull(convertedCategory);
        assertEquals(category.getName(), convertedCategory.getName());
        assertEquals(category.getDescription(), convertedCategory.getDescription());
        assertEquals(category.getAmountAllocated(), convertedCategory.getAmountAllocated());
        assertEquals(category.getDueDate(), convertedCategory.getDueDate());
    }

    @Test
    public void convertToCategoryEntityFailure() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           CategoryConverter.convertToCategoryEntity(null);
        });

        assertEquals("CategoryDto can not be null", exception.getMessage());
    }

    @Test
    public void convertToCategoryResponseSuccess() {
        UUID categoryId = UUID.randomUUID();
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        category.setAmountAllocated(BigDecimal.valueOf(2000));
        category.setAmountSpent(BigDecimal.ZERO);
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());

        CategoryResponse convertedCategory = CategoryConverter.convertToCategoryResponse(category);

        assertNotNull(convertedCategory);
        assertEquals(category.getId(), convertedCategory.getId());
        assertEquals(category.getName(), convertedCategory.getName());
        assertEquals(category.getDescription(), convertedCategory.getDescription());
        assertEquals(category.getAmountAllocated(), convertedCategory.getAmountAllocated());
        assertEquals(category.getDueDate(), convertedCategory.getDueDate());
    }

    @Test
    public void convertToCategoryResponseFailure() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            CategoryConverter.convertToCategoryResponse(null);
        });

        assertEquals("Category can not be null", exception.getMessage());
    }
}
