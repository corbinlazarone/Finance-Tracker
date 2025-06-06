package com.fintrackerapi.fintracker.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password  = password;
    }
}