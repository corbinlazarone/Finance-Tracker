package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.components.CategoryConverter;
import com.fintrackerapi.fintracker.components.CheckBudget;
import com.fintrackerapi.fintracker.components.TransactionConverter;
import com.fintrackerapi.fintracker.entities.Budget;
import com.fintrackerapi.fintracker.entities.Category;
import com.fintrackerapi.fintracker.entities.Transaction;
import com.fintrackerapi.fintracker.interfaces.CategoryInterface;
import com.fintrackerapi.fintracker.repositories.CategoryRepo;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService implements CategoryInterface {

    @Autowired
    private CheckBudget checkBudget;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    @Transactional
    public Set<CategoryResponse> getCategories(UUID userId) {
        Budget checkedBudget = checkBudget.checkBudgetExistsAndBelongsToUser(userId);

        Set<Category> categories = checkedBudget.getCategories();
        Set<CategoryResponse> categoryResponses = new HashSet<>();

        for (Category category: categories) {
            categoryResponses.add(CategoryConverter.convertToCategoryResponse(category));
        }

        return categoryResponses;
    }

    @Override
    public List<TransactionResponse> getTransactionsByCategory(UUID userId, UUID categoryId) {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        checkBudget.checkBudgetExistsAndBelongsToUser(userId);

        Optional<Category> foundCategory = categoryRepo.findById(categoryId);

        if (foundCategory.isPresent()) {
            List<Transaction> foundTransactions = foundCategory.get().getTransactions();
            foundTransactions.forEach(transaction -> {
                transactionResponses.add(TransactionConverter.convertToTransactionResponse(transaction));
            });
        }

        return transactionResponses;
    }
}
