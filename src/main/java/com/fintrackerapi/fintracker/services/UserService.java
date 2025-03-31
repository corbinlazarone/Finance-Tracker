package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.dtos.UserDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserDto> allUsers() {
        List<UserDto> usersDtos = new ArrayList<>();

        userRepo.findAll().forEach(user -> {
            usersDtos.add(convertToDto(user));
        });

        return usersDtos;
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}
