package com.github.tomaszgryczka.convey.authentication.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class LoginRequest {

    @NotBlank(message = "Username field cannot be blank")
    @Size(min = 3, max = 40)
    private String username;

    @NotBlank(message = "Password field cannot be blank")
    @Size(min = 3, max = 15)
    private String password;
}