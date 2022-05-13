package com.github.tomaszgryczka.convey.authentication.jwttoken.exception;

import com.github.tomaszgryczka.convey.authentication.response.AuthResponse;
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
    public ResponseEntity<AuthResponse> handleExpiredToken(ExpiredJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Expired JSON Web Token"));
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<AuthResponse> handleMalformedToken(MalformedJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Malformed JSON Web Token"));
    }

    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<AuthResponse> handleSignatureException(SignatureException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid JWT Token Signature"));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<AuthResponse> handleInvalidToken(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid JWT Token"));
    }

    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResponseEntity<AuthResponse> handleUnsupportedException(UnsupportedJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Unsupported JWT Exception"));
    }
}