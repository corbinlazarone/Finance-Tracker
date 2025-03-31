package com.fintrackerapi.fintracker.repositories;

import com.fintrackerapi.fintracker.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
