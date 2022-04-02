package com.github.tomaszgryczka.convey.authentication.login;

import com.github.tomaszgryczka.convey.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private UserService userService;

    @PostMapping("/session")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

//        String token = userService.signIn(loginRequest.getUsername(), loginRequest.getPassword());
//
//        return ResponseEntity.ok(new JWTAuthenticationResponse(token));

        return null;
    }

}
