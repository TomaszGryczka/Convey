package com.github.tomaszgryczka.convey.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void signUp(User user) {
        userRepository.save(user);
    }

    public String signIn(String username, String password, PasswordEncoder passwordEncoder) {

        Optional<User> user = userRepository.login(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String token = UUID.randomUUID().toString();
            
            user.get().setToken(token);
            userRepository.save(user.get());

            return token;
        }

        return "";
    }

    public User findByUsername(String username) {
        Optional<User> userFromDb = userRepository.findByUsername(username);

        if (userFromDb.isPresent()) {
            return userFromDb.get();
        } else {
            throw new UsernameNotFoundException("Username: " + username + " not found.");
        }
    }
}
