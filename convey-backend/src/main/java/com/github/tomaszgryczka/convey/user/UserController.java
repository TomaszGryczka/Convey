package com.github.tomaszgryczka.convey.user;

import com.github.tomaszgryczka.convey.authentication.login.MyUserDetails;
import com.github.tomaszgryczka.convey.authentication.response.AuthResponse;
import com.github.tomaszgryczka.convey.authentication.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/protected")
    public AuthResponse authenticateUser() {

        return new AuthResponse("User authenticated");
    }

    @GetMapping("/user/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal MyUserDetails userDetails) {

        return UserResponse
                .builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build();
    }

    @GetMapping("/user/contacts")
    public List<UserResponse> getUsers(@AuthenticationPrincipal MyUserDetails userDetails) {

        return userService
                .findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(userDetails.getUsername()))
                .map(userService::convert)
                .collect(Collectors.toList());
    }
}