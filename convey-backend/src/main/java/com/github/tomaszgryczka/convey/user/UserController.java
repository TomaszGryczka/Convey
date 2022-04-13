package com.github.tomaszgryczka.convey.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserService userService;

    @GetMapping("/chat")
    public ResponseEntity<?> authenticateUser() {

        return ResponseEntity.ok("hello world");
    }
}