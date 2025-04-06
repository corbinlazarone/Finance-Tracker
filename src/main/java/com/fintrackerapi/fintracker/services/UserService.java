package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.interfaces.UserInterface;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.UserResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserInterface {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserResponse> allUsers() {
        List<UserResponse> userResponses = new ArrayList<>();

        userRepo.findAll().forEach(user -> {
            userResponses.add(convertToUserResponse(user));
        });

        return userResponses;
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}
