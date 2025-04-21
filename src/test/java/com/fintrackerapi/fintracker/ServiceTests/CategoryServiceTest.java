package com.fintrackerapi.fintracker.ServiceTests;

import com.fintrackerapi.fintracker.components.CategoryConverter;
import com.fintrackerapi.fintracker.components.CheckBudget;
import com.fintrackerapi.fintracker.entities.Budget;
import com.fintrackerapi.fintracker.entities.Category;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.services.CategoryService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * TEST CASES
 * - Test get categories not empty
 * - Test get categories empty
 */

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryConverter categoryConverter;

    @Mock
    private CheckBudget checkBudget;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Category Test Name");
    }

    @Test
    public void getCategoriesNotEmpty() {
        UUID budgetId = UUID.randomUUID();

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Budget checkedBudget = new Budget();
        checkedBudget.setId(budgetId);
        checkedBudget.setName("Budget Test Name");

        Set<Category> categorySet = new HashSet<>();
        categorySet.add(category);
        checkedBudget.setCategories(categorySet);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName("Test Category");

        when(checkBudget.checkBudgetExistsAndBelongsToUser(userId)).thenReturn(checkedBudget);
        when(categoryConverter.convertToCategoryResponse(category)).thenReturn(categoryResponse);

        Set<CategoryResponse> result = categoryService.getCategories(userId);

        assertFalse(result.isEmpty());
        assertEquals(result.size(), 1);

        CategoryResponse indvidualResponse = result.iterator().next();

        assertEquals(indvidualResponse.getId(), categoryResponse.getId());
        assertEquals(indvidualResponse.getName(), categoryResponse.getName());
    }

    @Test
    public void getCategoriesEmpty() {
        UUID budgetId = UUID.randomUUID();

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Budget checkedBudget = new Budget();
        checkedBudget.setId(budgetId);
        checkedBudget.setName("Budget Test Name");
        checkedBudget.setCategories(new HashSet<>());

        when(checkBudget.checkBudgetExistsAndBelongsToUser(userId)).thenReturn(checkedBudget);

        Set<CategoryResponse> result = categoryService.getCategories(userId);

        assertTrue(result.isEmpty());
    }
}