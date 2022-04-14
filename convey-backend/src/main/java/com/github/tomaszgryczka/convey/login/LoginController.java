package com.github.tomaszgryczka.convey.login;

import com.github.tomaszgryczka.convey.login.jwttoken.JwtAuthResponse;
import com.github.tomaszgryczka.convey.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {

    private UserService userService;
    
    private PasswordEncoder passwordEncoder;

    @PostMapping("/session")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        String token = userService.signIn(loginRequest.getUsername(), loginRequest.getPassword(), passwordEncoder);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
}
