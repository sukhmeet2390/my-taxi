package com.mytaxi.exception;

public class DriverNotFoundException extends EntityNotFoundException {
    public DriverNotFoundException(String message) {
        super(message);
    }
}
