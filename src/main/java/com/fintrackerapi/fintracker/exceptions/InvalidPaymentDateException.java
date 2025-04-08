package com.fintrackerapi.fintracker.exceptions;

public class InvalidPaymentDateException extends FinTrackerException {
  private static final String DEFAULT_MESSAGE = "Invalid Payment Date. Must be greater than 1 and less than 31.";

  public InvalidPaymentDateException() {
    super(DEFAULT_MESSAGE);
  }

  public InvalidPaymentDateException(String message) {
    super(message);
  }
}
