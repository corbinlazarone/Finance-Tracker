package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.responses.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserInterface {
    List<UserResponse> allUsers();
}
