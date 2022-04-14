package com.github.tomaszgryczka.convey.login.jwttoken.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtExceptionHandlerAdvice {

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredToken(ExpiredJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Expired JSON Web Token");
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedToken(MalformedJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Malformed JSON Web Token");
    }

    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid JWT Token Signature");
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> handleInvalidToken(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid JWT Token");
    }

    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResponseEntity<?> handleUnsupportedException(UnsupportedJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Unsupported JWT Exception");
    }
}