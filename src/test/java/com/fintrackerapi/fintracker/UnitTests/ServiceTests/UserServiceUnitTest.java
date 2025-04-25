package com.fintrackerapi.fintracker.UnitTests.ServiceTests;

import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.UserResponse;
import com.fintrackerapi.fintracker.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TEST CASES
 *  - Test get all users for non-empty users
 *  - Test get all users for empty users
 *  - Test findByEmail success
 *  - Test findByEmail failure
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void getAllUsersSuccess() {

        // Setup mock data
        List<User> mockUsers = new ArrayList<>();

        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        User user1 = new User();
        user1.setId(userId1);
        user1.setFullName("Test User");
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setId(userId2);
        user2.setFullName("Another User");
        user2.setEmail("another@example.com");

        mockUsers.add(user1);
        mockUsers.add(user2);

        // Mock findAll to return mock users
        when(userRepo.findAll()).thenReturn(mockUsers);

        // Call the allUsers service method
        List<UserResponse> result = userService.allUsers();

        // Assert that the result is not null and has a size of two
        assertNotNull(result);
        assertEquals(2, result.size());

        // Assert that the user details are correct
        assertEquals(userId1, result.get(0).getId());
        assertEquals(user1.getFullName(), result.get(0).getFullName());
        assertEquals(user1.getEmail(), result.get(0).getEmail());
        assertEquals(userId2, result.get(1).getId());
        assertEquals(user2.getEmail(), result.get(1).getEmail());
        assertEquals(user2.getFullName(), result.get(1).getFullName());

        // Verify that user repo findAll was only called once
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void getAllUsersFail() {

        // Mock findAll to return empty users list
        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        // Call the allUsers service method
        List<UserResponse> result = userService.allUsers();

        // Assert that the result is empty
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify that user repo findAll was only called once
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void getFindByEmailSuccess() {

        // Mock User object
        UUID mockUserId = UUID.randomUUID();
        Date mockUserCreatedAt = new Date();
        User mockUser = new User();
        mockUser.setId(mockUserId);
        mockUser.setFullName("Test test");
        mockUser.setEmail("test@example.com");
        mockUser.setCreatedAt(mockUserCreatedAt);

        // Mock findByEmail to return optional of mock user object
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        // Call the findByEmail service method
        UserResponse foundUser = userService.findByEmail("test@example.com");

        // Assert that the result is not null
        assertNotNull(foundUser);

        // Assert that user response details are correct
        assertEquals(foundUser.getId(), mockUser.getId());
        assertEquals(foundUser.getFullName(), mockUser.getFullName());
        assertEquals(foundUser.getEmail(), mockUser.getEmail());
        assertEquals(foundUser.getCreatedAt(), mockUser.getCreatedAt());

        // Verify that the findByEmail method was only called once
        verify(userRepo, times(1)).findByEmail("test@example.com");
    }

    @Test
     public void getFindByEmailFailure() {

        // Mock findByEmail to return optional of user object
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Call the findByEmail service method expecting an exception to be thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
           userService.findByEmail("test@example.com");
        });

        // Assert that the exception message matches
        assertEquals("No user found", exception.getMessage());

        // Verify that the findByEmail method was only called once
        verify(userRepo, times(1)).findByEmail("test@example.com");
    }
}
