package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CategoryInterface {
    // get category by id
    Set<CategoryResponse> getCategories(UUID userId);
    // get all transactions by category id
    List<TransactionResponse> getTransactionsByCategory(UUID userId, UUID categoryId);
    // create a new category for a budget
    // update category by category id
    // delete a category by budget
}
