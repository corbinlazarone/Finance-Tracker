package com.fintrackerapi.fintracker.exceptions;

public class FinTrackerException extends RuntimeException {

    public FinTrackerException(String message) {
        super(message);
    }

    public FinTrackerException(String message, Throwable cause) {
        super(message, cause);
    }
}
