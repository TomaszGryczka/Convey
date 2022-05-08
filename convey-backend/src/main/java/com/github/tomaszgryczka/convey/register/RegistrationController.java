package com.github.tomaszgryczka.convey.register;

import com.github.tomaszgryczka.convey.response.AuthResponse;
import com.github.tomaszgryczka.convey.user.User;
import com.github.tomaszgryczka.convey.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        User user = registrationRequest.toUser(passwordEncoder);

        userService.signUp(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(new AuthResponse("User registered!"));
    }
}
