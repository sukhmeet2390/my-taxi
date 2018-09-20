package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car Already in use by some other driver.")
public class CarAlreadyInUseException extends Exception {
    private static final long serialVersionUID = -3387516994444229948L;

    public CarAlreadyInUseException(String message) {
        super(message);
    }
}
