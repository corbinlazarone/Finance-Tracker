package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.dtos.LoginUserDto;
import com.fintrackerapi.fintracker.dtos.RegisterUserDto;
import com.fintrackerapi.fintracker.entities.User;

public interface AuthenticationInterface {
    User signUp(RegisterUserDto input);
    User authenticate(LoginUserDto input);
}
