package com.fintrackerapi.fintracker.repositories;

import com.fintrackerapi.fintracker.entities.Income;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface IncomeRepo extends CrudRepository<Income, UUID> {
    List<Income> findByUserId(UUID userId);
}
