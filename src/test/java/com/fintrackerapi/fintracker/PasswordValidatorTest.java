package com.fintrackerapi.fintracker;

import com.fintrackerapi.fintracker.utils.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST CASES
 * - Test a valid password
 * - Test a password that is too short
 * - Test a password that does not have a letter
 * - Test a password that does not have a number
 * - Test a password that does not have a special character
 */

public class PasswordValidatorTest {

    @Test
    public void isValidPassword() {
        assertDoesNotThrow(() -> PasswordValidator.passwordValidator("TruePassword123!"));
    }

    @Test
    public void isTooShort() {
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            PasswordValidator.passwordValidator("Short1!");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }

    @Test
    public void doesNotHaveALetter() {
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            PasswordValidator.passwordValidator("1234567!");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }

    @Test
    public void doesNotHaveANumber() {

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            PasswordValidator.passwordValidator("abcdfgk!");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }

    @Test
    public void doesNotHaveASpecialCharacter() {

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            PasswordValidator.passwordValidator("TestPass12");
        });

        assertEquals( "Password must be at least 8 characters and include both letters, " +
                "numbers and one special character", exception.getMessage());
    }
}
