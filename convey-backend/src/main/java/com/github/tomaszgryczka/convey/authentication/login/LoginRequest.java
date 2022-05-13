package com.github.tomaszgryczka.convey.authentication.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;
}