package com.fintrackerapi.fintracker.repositories;

import com.fintrackerapi.fintracker.entities.Budget;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BudgetRepo extends CrudRepository<Budget, UUID> {
    Budget findByUserId(UUID userId);
}
