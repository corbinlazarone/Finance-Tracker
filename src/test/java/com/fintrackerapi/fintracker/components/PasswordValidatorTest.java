package com.fintrackerapi.fintracker.components;

import com.fintrackerapi.fintracker.exceptions.NotPermittedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST CASES
 * - Test a valid password
 * - Test a null password
 * - Test a password that is too short
 * - Test a password that does not have a letter
 * - Test a password that does not have a number
 * - Test a password that does not have a special character
 */

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {

    private final PasswordValidator passwordValidator = new PasswordValidator();

    @Test
    public void isValidPassword() {
        assertDoesNotThrow(() -> passwordValidator.Validator("TruePassword123!"));
    }

    @Test
    public void isPasswordNull() {
        NotPermittedException exception = assertThrows(NotPermittedException.class, () -> {
            passwordValidator.Validator(null);
        });

        assertEquals("Password can not be null", exception.getMessage());
    }

    @Test
    public void isTooShort() {
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            passwordValidator.Validator("Short1!");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }

    @Test
    public void doesNotHaveALetter() {
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            passwordValidator.Validator("1234567!");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }

    @Test
    public void doesNotHaveANumber() {

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            passwordValidator.Validator("abcdfgk!");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }

    @Test
    public void doesNotHaveASpecialCharacter() {

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            passwordValidator.Validator("TestPass12");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }
}