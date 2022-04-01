package com.github.tomaszgryczka.convey.authentication.login;

import com.github.tomaszgryczka.convey.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}