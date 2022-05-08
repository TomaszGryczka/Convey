package com.github.tomaszgryczka.convey.user;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String response;

    public AuthResponse(String response) {
        this.response = response;
    }
}
