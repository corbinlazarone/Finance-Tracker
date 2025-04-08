package com.fintrackerapi.fintracker.repositories;

import com.fintrackerapi.fintracker.entities.Budget;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BudgetRepo extends CrudRepository<Budget, UUID> {
    Optional<Budget> findByUserId(UUID userId);
}
