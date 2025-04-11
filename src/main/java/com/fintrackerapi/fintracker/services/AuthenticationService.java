package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.dtos.LoginUserDto;
import com.fintrackerapi.fintracker.dtos.RegisterUserDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
import com.fintrackerapi.fintracker.interfaces.AuthenticationInterface;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.UserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationInterface {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserResponse signUp(RegisterUserDto input) {
        // Basic null checks
        if (input.getEmail() == null || input.getPassword() == null || input.getFullName() == null) {
            throw new BadCredentialsException("All fields are required");
        }

        // Trim inputs and check if empty
        if (input.getFullName().trim().isEmpty()) {
            throw new BadCredentialsException("Full name cannot be empty");
        }

        // Validate email format
        if (!isValidEmail(input.getEmail())) {
            throw new BadCredentialsException("Invalid email format");
        }

        // Check if email already exists
        if (userRepo.findByEmail(input.getEmail()).isPresent()) {
            throw new BadCredentialsException("Email already registered");
        }

        // Validate password strength
        if (!isStrongPassword(input.getPassword())) {
            throw new BadCredentialsException(
                    "Password must be at least 8 characters and include both letters, " +
                            "numbers and one special character"
            );
        }

        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        User savedUser = userRepo.save(user);

        return convertToUserResponse(savedUser);
    }

    @Override
    public User authenticate(LoginUserDto input) {
        if (input.getEmail() == null || input.getPassword() == null) {
            throw new BadCredentialsException("Invalid email or password");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepo.findByEmail(input.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user"));
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(regex);
    }

    private boolean isStrongPassword(String password) {
        // Minimum 8 chars, at least one letter, one number, and one special character
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&_\\-#])[A-Za-z\\d@$!%*?&_\\-#]{8,}$";
        return password != null && password.matches(regex);
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}