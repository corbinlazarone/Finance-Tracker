package com.fintrackerapi.fintracker.ServiceTests;

import com.fintrackerapi.fintracker.entities.User;
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
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

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
}
