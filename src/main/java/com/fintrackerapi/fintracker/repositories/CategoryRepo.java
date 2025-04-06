package com.fintrackerapi.fintracker.repositories;

import com.fintrackerapi.fintracker.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryRepo extends CrudRepository<Category, UUID> {
}
