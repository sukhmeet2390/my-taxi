package com.mytaxi.exceptionhandler;

import com.mytaxi.datatransferobject.GenericErrorDTO;
import com.mytaxi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {
    @ExceptionHandler(CarAlreadyInUseException.class)
    public ResponseEntity<GenericErrorDTO> carAlreadyInUseHandler(CarAlreadyInUseException e) {
        final GenericErrorDTO err = new GenericErrorDTO(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<GenericErrorDTO> carNotFoundHandler(CarNotFoundException e) {
        final GenericErrorDTO err = new GenericErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ConstraintsViolationException.class)
    public ResponseEntity<GenericErrorDTO> constraintsViolationHandler(ConstraintsViolationException e) {
        final GenericErrorDTO err = new GenericErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<GenericErrorDTO> driverNotFoundHandler(DriverNotFoundException e) {
        final GenericErrorDTO err = new GenericErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DriverOfflineException.class)
    public ResponseEntity<GenericErrorDTO> driverOfflineHandler(DriverOfflineException e) {
        final GenericErrorDTO err = new GenericErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GenericErrorDTO> entityNotFoundHandler(CarAlreadyInUseException e) {
        final GenericErrorDTO err = new GenericErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorDTO> exceptionHandler(Exception e) {
        log.error("Unhandled Error occurred: " + e);
        final GenericErrorDTO err = new GenericErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
