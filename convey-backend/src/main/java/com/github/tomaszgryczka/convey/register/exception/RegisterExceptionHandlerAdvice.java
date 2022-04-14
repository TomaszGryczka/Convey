package com.github.tomaszgryczka.convey.register.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegisterExceptionHandlerAdvice {

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserExists(UserAlreadyExistException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
