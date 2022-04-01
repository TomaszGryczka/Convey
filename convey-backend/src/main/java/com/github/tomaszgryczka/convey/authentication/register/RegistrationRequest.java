package com.github.tomaszgryczka.convey.authentication.register;

import com.github.tomaszgryczka.convey.user.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @NotBlank
    @Size(min = 3, max = 40)
    private String username;

    @NotBlank
    @Size(min = 3, max = 15)
    private String password;

    @NotBlank
    @Size(min = 3, max = 40)
    @Email
    private String email;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), email);
    }
}
