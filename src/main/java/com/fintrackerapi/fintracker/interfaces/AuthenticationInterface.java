package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.dtos.LoginUserDto;
import com.fintrackerapi.fintracker.dtos.RegisterUserDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.responses.UserResponse;

public interface AuthenticationInterface {
    UserResponse signUp(RegisterUserDto input);
    User authenticate(LoginUserDto input);
}
