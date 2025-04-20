package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.components.CategoryConverter;
import com.fintrackerapi.fintracker.components.CheckBudget;
import com.fintrackerapi.fintracker.entities.Budget;
import com.fintrackerapi.fintracker.entities.Category;
import com.fintrackerapi.fintracker.interfaces.CategoryInterface;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CategoryService implements CategoryInterface {

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private CheckBudget checkBudget;

    @Override
    @Transactional
    public Set<CategoryResponse> getCategories(UUID userId) {
        Budget checkedBudget = checkBudget.checkBudgetExistsAndBelongsToUser(userId);

        Set<Category> categories = checkedBudget.getCategories();
        Set<CategoryResponse> categoryResponses = new HashSet<>();

        for (Category category: categories) {
            categoryResponses.add(categoryConverter.convertToCategoryResponse(category));
        }

        return categoryResponses;
    }
}
