package com.github.tomaszgryczka.convey.authentication;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JWTAuthenticationResponse {

    @NonNull
    private String token;
}
