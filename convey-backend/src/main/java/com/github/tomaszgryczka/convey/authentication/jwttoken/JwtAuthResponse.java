package com.github.tomaszgryczka.convey.authentication.jwttoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class JwtAuthResponse {

    @NotBlank
    private String token;

    private final String tokenType = "Bearer";
}
