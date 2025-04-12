package com.fintrackerapi.fintracker.ServiceTests;


import com.fintrackerapi.fintracker.dtos.LoginUserDto;
import com.fintrackerapi.fintracker.dtos.RegisterUserDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.UserResponse;
import com.fintrackerapi.fintracker.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TEST CASES
 * - Test successful user registration
 * - Test user registration with existing email
 * - Test user registration with invalid input
 * - Test successful user login
 * - Test user login with invalid input
 * - Test user login with non-existent user
 * - Test exception handling
 */

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private static final UUID USERID = UUID.randomUUID();
    private static final String TEST_EMAIL = "test@gmai.com";
    private static final String TEST_FULL_NAME = "Test1 Test";
    private static final String TEST_PASSWORD = "Demons042204!";
    private LoginUserDto loginUserDto;
    private RegisterUserDto registerUserDto;

    @BeforeEach
    void setUp() {
        // Build login user dto and register user dto
        loginUserDto = new LoginUserDto(
                TEST_EMAIL,
                TEST_PASSWORD
        );

        registerUserDto = new RegisterUserDto(
            TEST_EMAIL,
            TEST_PASSWORD,
            TEST_FULL_NAME
        );
    }

    @Test
    public void userRegistrationSuccess() {

        User savedUser = new User();
        savedUser.setId(USERID);
        savedUser.setEmail(registerUserDto.getEmail());
        savedUser.setFullName(registerUserDto.getFullName());
        savedUser.setCreatedAt(new Date());

        // Mock User entity for convertToUserResponse method
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        // Mock password encoder
        when(passwordEncoder.encode(registerUserDto.getPassword())).thenReturn("encodedPassword");

         // Mock the findByEmail method to return null,
         // simulating that a user does not exist with the provided email
        when(userRepo.findByEmail(registerUserDto.getEmail())).thenReturn(Optional.empty());

        // Call the register method
        UserResponse newUser = authenticationService.signUp(registerUserDto);

        // Assert that the result is not null
        assertNotNull(newUser);

        // Assert that the user details are correct
        assertEquals(registerUserDto.getEmail(), newUser.getEmail());
        assertEquals(registerUserDto.getFullName(), newUser.getFullName());

        // Verify that the userRepo findByEmail was only called once
        verify(userRepo, times(1)).findByEmail(registerUserDto.getEmail());

        // Verify that the was only saved once
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void userRegistrationWithExistingEmail() {

        User existingUser = new User();
        existingUser.setId(USERID);
        existingUser.setEmail(registerUserDto.getEmail());
        existingUser.setFullName(registerUserDto.getFullName());
        existingUser.setPassword("encodedPassword");

        // Mock the findByEmail method to return a User object
        // simulating that a user already exist with the provided email
        when(userRepo.findByEmail(registerUserDto.getEmail())).thenReturn(Optional.of(existingUser));

        // Call the register method expecting an exception to be thrown
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authenticationService.signUp(registerUserDto);
        });

        // Assert that the exception message matches
        assertEquals("Email already registered", exception.getMessage());

        // Verify that the user repo findByEmail method was called
        verify(userRepo, times(1)).findByEmail(registerUserDto.getEmail());

        // Verify that the user was never saved
        verify(userRepo, never()).save(any(User.class));
    }
}
