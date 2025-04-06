package com.fintrackerapi.fintracker.repositories;

import com.fintrackerapi.fintracker.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionRepo extends CrudRepository<Transaction, UUID> {
}
