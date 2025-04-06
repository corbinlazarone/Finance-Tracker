package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.dtos.BudgetDto;
import com.fintrackerapi.fintracker.interfaces.BudgetInterface;
import com.fintrackerapi.fintracker.responses.BudgetResponse;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class BudgetService implements BudgetInterface {

    @Override
    public BudgetResponse getBudget(UUID userId) {
        return null;
    }

    @Override
    public BudgetResponse createNewBudget(UUID userId, BudgetDto budgetDto) {
        return null;
    }

    @Override
    public Set<CategoryResponse> getCategories(UUID userId) {
        return Set.of();
    }

    @Override
    public Set<TransactionResponse> getAllTransactions(UUID userId) {
        return Set.of();
    }

    @Override
    public boolean deleteBudget(UUID userId) {
        return true;
    }
}
