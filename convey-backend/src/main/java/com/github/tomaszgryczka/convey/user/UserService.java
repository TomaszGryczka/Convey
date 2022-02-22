package com.github.tomaszgryczka.convey.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public void signUp(User user) {
        userRepository.save(user);
    }
}
