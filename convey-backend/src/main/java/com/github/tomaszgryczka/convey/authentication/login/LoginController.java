package com.github.tomaszgryczka.convey.authentication.login;

import com.github.tomaszgryczka.convey.authentication.jwttoken.JwtAuthResponse;
import com.github.tomaszgryczka.convey.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/session")
    public JwtAuthResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

        String token = userService.signIn(loginRequest.getUsername(), loginRequest.getPassword(), passwordEncoder);

        return new JwtAuthResponse(token);
    }
}
