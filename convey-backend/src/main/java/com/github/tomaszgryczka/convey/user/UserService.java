package com.github.tomaszgryczka.convey.user;

import com.github.tomaszgryczka.convey.authentication.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private JwtTokenUtil jwtTokenUtil;

    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    public void signUp(User user) {
        userRepository.save(user);
    }

    public String signIn(String username, String password, PasswordEncoder passwordEncoder) {

        Optional<User> user = userRepository.login(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            return jwtTokenUtil.generateToken(authentication);
        } else {
            System.out.println("error");
            return "";
        }
    }
}
