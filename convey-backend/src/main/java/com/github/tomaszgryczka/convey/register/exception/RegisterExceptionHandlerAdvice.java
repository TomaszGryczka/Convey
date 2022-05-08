package com.github.tomaszgryczka.convey.register.exception;

import com.github.tomaszgryczka.convey.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegisterExceptionHandlerAdvice {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserExists(UserAlreadyExistsException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new AuthResponse(e.getMessage()));
    }
}
