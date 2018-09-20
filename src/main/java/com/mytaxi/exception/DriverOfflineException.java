package com.mytaxi.exception;

public class DriverOfflineException extends ConstraintsViolationException {
    public DriverOfflineException(String message) {
        super(message);
    }
}
