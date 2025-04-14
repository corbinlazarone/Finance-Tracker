package com.fintrackerapi.fintracker.utils;

import org.springframework.security.authentication.BadCredentialsException;

public class PasswordValidator {
    public static void passwordValidator(String password) {
        // Minimum 8 chars, at least one letter, one number, and one special character
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&_\\-#])[A-Za-z\\d@$!%*?&_\\-#]{8,}$";
        if (!password.matches(regex)) {
            throw new BadCredentialsException(
                    "Password must be at least 8 characters and include both letters, " +
                            "numbers and one special character"
            );
        }
    }
}
