package com.fintrackerapi.fintracker.ServiceTests;


import com.fintrackerapi.fintracker.dtos.LoginUserDto;
import com.fintrackerapi.fintracker.dtos.RegisterUserDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
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
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_FULL_NAME = "Test1 Test";
    private static final String TEST_PASSWORD = "TestPassword123!";
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

    @Test
    public void userRegistrationNullInput() {

        RegisterUserDto invalidUserDto = new RegisterUserDto(
                TEST_EMAIL,
                null,
                ""
        );

        // Call the register method expecting an exception to be thrown
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
           authenticationService.signUp(invalidUserDto);
        });

        // Assert that the exception message matches
        assertEquals("All fields are required", exception.getMessage());

        // Verify that the user repo findByEmail was never called
        verify(userRepo, times(0)).findByEmail(invalidUserDto.getEmail());

        // Verify that the user was never saved
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void userRegistrationEmptyValues() {

        RegisterUserDto invalidUserDto = new RegisterUserDto(
                TEST_EMAIL,
                TEST_PASSWORD,
                ""
        );

        // Call the register method expecting an exception to be thrown
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authenticationService.signUp(invalidUserDto);
        });

        // Assert that the exception message matches
        assertEquals("Full name cannot be empty", exception.getMessage());

        // Verify that the user repo findByEmail was never called
        verify(userRepo, times(0)).findByEmail(invalidUserDto.getEmail());

        // Verify that the user was never saved
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void userRegistrationInvalidEmailFormat() {

        RegisterUserDto invalidUserDto = new RegisterUserDto(
                "invalidEmail",
                TEST_PASSWORD,
                TEST_FULL_NAME
        );

        // Call the register method expecting an exception to be thrown
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authenticationService.signUp(invalidUserDto);
        });

        // Assert that the exception message matches
        assertEquals("Invalid email format", exception.getMessage());

        // Verify that the user repo findByEmail was never called
        verify(userRepo, times(0)).findByEmail(invalidUserDto.getEmail());

        // Verify that the user was never saved
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void userLoginSuccess() {

        // Mock user
        UUID mockUserId = UUID.randomUUID();
        Date mockUserCreatedAt = new Date();
        User mockUser = new User();
        mockUser.setId(mockUserId);
        mockUser.setFullName("Test1 Test");
        mockUser.setEmail(loginUserDto.getEmail());
        mockUser.setCreatedAt(mockUserCreatedAt);

        // Mock findByEmail method to return mock user
        when(userRepo.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.of(mockUser));

        // Call service method to authenticate user
        User foundUser = authenticationService.authenticate(loginUserDto);

        // Assert that the result is not null
        assertNotNull(foundUser);

        // Assert that the result details are correct
        assertEquals(foundUser.getId(), mockUser.getId());
         assertEquals(foundUser.getFullName(), mockUser.getFullName());
        assertEquals(foundUser.getEmail(), mockUser.getEmail());
        assertEquals(foundUser.getCreatedAt(), mockUser.getCreatedAt());

        // Verify that the user repo findByEmail was only called once
        verify(userRepo, times(1)).findByEmail(loginUserDto.getEmail());
    }

    @Test
    public void userLoginWithInvalidInput() {

        // Mock user login dto with invalid inputs
        LoginUserDto loginUserDtoInvalid = new LoginUserDto(
            null,
            null
        );

        // Call the authenticate service method expecting an exception to be thrown
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
           authenticationService.authenticate(loginUserDtoInvalid);
        });

        // Assert that exception message matches
        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    public void userLoginWithNonExistentUser() {

        // Mock findByEmail to return empty user object
        when(userRepo.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.empty());

        // Call the authenticate service method expecting an exception to be thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
           authenticationService.authenticate(loginUserDto);
        });

        // Assert that the exception message matches
        assertEquals("Could not find user", exception.getMessage());
    }
}
