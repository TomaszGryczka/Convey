package com.github.tomaszgryczka.convey.chatuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public void signUp(User chatUser) {
        userRepository.save(chatUser);
    }
}
