package com.github.tomaszgryczka.convey.user;

import com.github.tomaszgryczka.convey.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @PostMapping("/protected")
    public ResponseEntity<?> authenticateUser() {

        return ResponseEntity.ok(new AuthResponse("ok"));
    }
}