package com.github.tomaszgryczka.convey.user;

import com.github.tomaszgryczka.convey.authentication.login.LoginRequest;
import com.github.tomaszgryczka.convey.authentication.login.MyUserDetails;
import com.github.tomaszgryczka.convey.authentication.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal MyUserDetails userDetails) {

        return UserResponse
                .builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build();
    }

    @GetMapping("/contacts")
    public List<UserResponse> getUsers(@AuthenticationPrincipal MyUserDetails userDetails) {

        return userService
                .findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(userDetails.getUsername()))
                .map(userService::convert)
                .toList();
    }

    @PostMapping("/password")
    public void changePassword(@Valid @RequestBody PasswordRequest passwordRequest) {

        userService.changePassword(passwordRequest.getUsername(), passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
    }
}