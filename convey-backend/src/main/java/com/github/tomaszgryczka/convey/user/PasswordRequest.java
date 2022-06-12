package com.github.tomaszgryczka.convey.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class PasswordRequest {

    @NotBlank(message = "Username field cannot be blank")
    @Size(min = 3, max = 40)
    private String username;

    @NotBlank(message = "Old password field cannot be blank")
    @Size(min = 3, max = 15)
    private String oldPassword;

    @NotBlank(message = "New password field cannot be blank")
    @Size(min = 3, max = 15)
    private String newPassword;
}
