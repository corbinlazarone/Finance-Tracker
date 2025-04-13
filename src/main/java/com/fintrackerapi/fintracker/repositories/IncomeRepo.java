package com.fintrackerapi.fintracker.repositories;

import com.fintrackerapi.fintracker.entities.Income;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepo extends CrudRepository<Income, UUID> {
    List<Income> findByUserId(UUID userId);
}
