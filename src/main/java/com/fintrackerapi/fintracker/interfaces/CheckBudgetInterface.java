package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.entities.Budget;

import java.util.UUID;

public interface CheckBudgetInterface {
    Budget checkBudgetExistsAndBelongsToUser(UUID userId);
}
