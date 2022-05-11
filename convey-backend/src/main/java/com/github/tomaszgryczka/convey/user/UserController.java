package com.github.tomaszgryczka.convey.user;

import com.github.tomaszgryczka.convey.login.MyUserDetails;
import com.github.tomaszgryczka.convey.response.AuthResponse;
import com.github.tomaszgryczka.convey.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal MyUserDetails userDetails) {

        return ResponseEntity.ok(
                UserResponse
                        .builder()
                        .id(userDetails.getId().toString())
                        .username(userDetails.getUsername())
                        .build());
    }

    @GetMapping("/user/contacts")
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal MyUserDetails userDetails) {

        return ResponseEntity.ok(
                userService
                        .findAll()
                        .stream()
                        .filter(user -> !user.getUsername().equals(userDetails.getUsername()))
                        .map(userService::convert));
    }
}