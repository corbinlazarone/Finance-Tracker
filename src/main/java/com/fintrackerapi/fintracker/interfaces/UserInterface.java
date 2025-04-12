package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.responses.UserResponse;

import java.util.List;

public interface UserInterface {
    List<UserResponse> allUsers();
    UserResponse findByEmail(String email);
}
