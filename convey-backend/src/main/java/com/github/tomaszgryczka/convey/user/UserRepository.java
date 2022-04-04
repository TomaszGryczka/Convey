package com.github.tomaszgryczka.convey.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);

    @Query(value = "SELECT u From User u WHERE u.username = ?1")
    public Optional<User> login(String username);
}
