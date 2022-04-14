package com.github.tomaszgryczka.convey.login.jwttoken.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtExceptionHandlerAdvice {

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredToken(ExpiredJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Expired JWT");
    }
}
