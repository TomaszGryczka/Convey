package com.github.tomaszgryczka.convey.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u From User u WHERE u.username = ?1")
    Optional<User> login(String username);

    @Modifying
    @Query("UPDATE User u SET u.password = ?2 WHERE u.username = ?1")
    @Transactional
    void updatePasswordForUser(String username, String password);
}
