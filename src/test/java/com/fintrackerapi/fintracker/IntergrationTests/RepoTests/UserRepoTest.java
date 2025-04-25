package com.fintrackerapi.fintracker.IntergrationTests.RepoTests;

import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  TEST CASES
 *  - Test save method
 *  - Test findByEmail method for existing user
 *  - Test findByEmail method for non-existing user
 */

@DataJpaTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    @Transactional
    @Rollback
    public void testSaveUser() {

        String userEmail = "test1@gmail.com";
        String userFullName = "Test1 test";
        String userPassword = "testPassword";

        User user = new User();
        user.setEmail(userEmail);
        user.setFullName(userFullName);
        user.setPassword(userPassword);

        User savedUser = userRepo.save(user);

        // Assert that the retrieved user is not null
        assertNotNull(savedUser);

        // Assert that the retrieved user id is not null
        assertNotNull(savedUser.getId());

        // Assert that the retrieved user's name matches the expected name
        assertEquals(userFullName, savedUser.getFullName());

        // Assert that the retrieved user's email matches the expected email
        assertEquals(userEmail, savedUser.getEmail());

        // Assert that the retrieved user's password matches the expected password
        assertEquals(userPassword, savedUser.getPassword());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByEmailUserFound() {

        String userEmail = "test1@gmail.com";
        String userFullName = "Test1 test";
        String userPassword = "testPassword";

        User user = new User();
        user.setEmail(userEmail);
        user.setFullName(userFullName);
        user.setPassword(userPassword);

        userRepo.save(user);

        // Retrieve the user from the database using findByEmail
        Optional<User> foundUserOptional = userRepo.findByEmail(userEmail);

        // Assert that the user was found
        assertTrue(foundUserOptional.isPresent());

        // Get user from optional
        User foundUser = foundUserOptional.get();

        // Assert that the retrieved user's email matches the expected email
        assertEquals(userEmail, foundUser.getEmail());

        // Assert that the retrieved user's name matches the expected name
        assertEquals(userFullName, foundUser.getFullName());
    }

    @Test
    @Transactional
    @Rollback
    public void testFinByEmailUserNotFound() {
        Optional<User> foundUserOptional = userRepo.findByEmail("no.user@gmail.com");

        // Assert that the user was not found
        assertFalse(foundUserOptional.isPresent());
    }
}
