package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.components.PasswordValidator;
import com.fintrackerapi.fintracker.dtos.LoginUserDto;
import com.fintrackerapi.fintracker.dtos.RegisterUserDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
import com.fintrackerapi.fintracker.interfaces.AuthenticationInterface;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationInterface {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordValidator passwordValidator;

    @Override
    public UserResponse signUp(RegisterUserDto input) {
        basicNullChecksForRegisterUserInput(input);
        checkForEmptyValues(input);
        isValidEmail(input.getEmail());
        checkIfEmailAlreadyExists(input);
        passwordValidator.Validator(input.getPassword());
        User user = convertRegisterDtoToUserEntity(input);
        User savedUser = userRepo.save(user);

        return convertToUserResponse(savedUser);
    }

    @Override
    public User authenticate(LoginUserDto input) {
        basicNullChecksForLoginUserInput(input);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepo.findByEmail(input.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user"));
    }

    private void basicNullChecksForRegisterUserInput(RegisterUserDto input) {
        if (input.getEmail() == null || input.getPassword() == null || input.getFullName() == null) {
            throw new BadCredentialsException("All fields are required");
        }
    }

    private void checkForEmptyValues(RegisterUserDto input) {
        if (input.getEmail().isEmpty() || input.getPassword().isEmpty() || input.getFullName().trim().isEmpty()) {
            throw new BadCredentialsException("Full name cannot be empty");
        }
    }

    private void isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(regex)) {
            throw new BadCredentialsException("Invalid email format");
        }
    }

    private void checkIfEmailAlreadyExists(RegisterUserDto input) {
        if (userRepo.findByEmail(input.getEmail()).isPresent()) {
            throw new BadCredentialsException("Email already registered");
        }
    }

    private User convertRegisterDtoToUserEntity(RegisterUserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    private void basicNullChecksForLoginUserInput(LoginUserDto input) {
        if (input.getEmail() == null || input.getPassword() == null) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}