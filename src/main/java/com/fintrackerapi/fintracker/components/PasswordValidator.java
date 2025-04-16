package com.fintrackerapi.fintracker.components;

import com.fintrackerapi.fintracker.exceptions.NotPermittedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    public void Validator(String password) {
        if (password == null) {
            throw new NotPermittedException("Password can not be null");
        }

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
