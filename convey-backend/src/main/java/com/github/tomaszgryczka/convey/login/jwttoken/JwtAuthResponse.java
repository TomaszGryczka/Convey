package com.github.tomaszgryczka.convey.login.jwttoken;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtAuthResponse {

    @NonNull
    private String token;

    private final String tokenType = "Bearer";
}
