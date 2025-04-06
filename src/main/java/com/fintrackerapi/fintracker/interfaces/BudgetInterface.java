package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.dtos.BudgetDto;
import com.fintrackerapi.fintracker.responses.BudgetResponse;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;

import java.util.Set;
import java.util.UUID;

public interface BudgetInterface {
    BudgetResponse getBudget(UUID userId);
    BudgetResponse createNewBudget(UUID userId, BudgetDto budgetDto);
    Set<CategoryResponse> getCategories(UUID userId);
    Set<TransactionResponse> getAllTransactions(UUID userId);
    boolean deleteBudget(UUID userId);
}
