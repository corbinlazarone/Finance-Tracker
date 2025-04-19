package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.dtos.BudgetDto;
import com.fintrackerapi.fintracker.dtos.CategoryDto;
import com.fintrackerapi.fintracker.dtos.TransactionDto;
import com.fintrackerapi.fintracker.responses.BudgetResponse;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;

import java.util.Set;
import java.util.UUID;

/**
 * Budget Service should have
 *  - get Budget
 *  - get all categories for budget
 *  - update budget
 *  - delete a budget
 */

public interface BudgetInterface {
    BudgetResponse getBudget(UUID userId);
    BudgetResponse createNewBudget(UUID userId, BudgetDto budgetDto);
    CategoryResponse createNewCategory(UUID userId, CategoryDto categoryDto);
    TransactionResponse createNewTransaction(UUID userId, UUID categoryId, TransactionDto transactionDto);
    Set<CategoryResponse> getCategories(UUID userId);
    Set<TransactionResponse> getAllTransactions(UUID userId);
    BudgetResponse updateBudget(UUID userId, BudgetDto budgetDto);
    CategoryResponse updateCategories(UUID userId, UUID categoryId, CategoryDto categoryDto);
    TransactionResponse updateTransaction(UUID userId, UUID transactionId, TransactionDto transactionDto);
    boolean deleteCategory(UUID userId, UUID categoryId);
    boolean deleteTransaction(UUID userId, UUID transactionId);
    boolean deleteBudget(UUID userId, UUID budgetId);
}
