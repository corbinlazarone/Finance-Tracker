package com.fintrackerapi.fintracker.components;

import com.fintrackerapi.fintracker.entities.Budget;
import com.fintrackerapi.fintracker.exceptions.NotPermittedException;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
import com.fintrackerapi.fintracker.interfaces.CheckBudgetInterface;
import com.fintrackerapi.fintracker.repositories.BudgetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CheckBudget implements CheckBudgetInterface {

    @Autowired
    private BudgetRepo budgetRepo;

    @Override
    public Budget checkBudgetExistsAndBelongsToUser(UUID userId) {
        Budget budget = budgetRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget does not exists"));

        UUID budgetUserId = budget.getUser().getId();
        if (!budgetUserId.equals(userId)) {
            throw new NotPermittedException("You don't have permission to update this budget!");
        }

        return budget;
    }
}