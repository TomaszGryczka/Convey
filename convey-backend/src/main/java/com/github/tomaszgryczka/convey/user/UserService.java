package com.github.tomaszgryczka.convey.user;

import com.github.tomaszgryczka.convey.authentication.jwttoken.JwtTokenUtil;
import com.github.tomaszgryczka.convey.authentication.register.RegistrationRequest;
import com.github.tomaszgryczka.convey.authentication.register.exception.UserAlreadyExistsException;
import com.github.tomaszgryczka.convey.authentication.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserResponse signUp(RegistrationRequest registrationRequest) {

        User user = registrationRequest.toUser(passwordEncoder);

        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb.isPresent()) {
            throw new UserAlreadyExistsException("User " + user.getUsername() + " already exists");
        }

        User newUser = userRepository.save(user);

        return UserResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .build();
    }

    public String signIn(String username, String password, PasswordEncoder passwordEncoder) {

        Optional<User> user = userRepository.login(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            return jwtTokenUtil.generateToken(authentication);
        } else {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserResponse convert(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
