package com.github.tomaszgryczka.convey.login.jwttoken.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private String response;

    public ExceptionResponse(String response) {
        this.response = response;
    }
}
