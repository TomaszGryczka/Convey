package com.github.tomaszgryczka.convey.chat.room;

import com.github.tomaszgryczka.convey.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipentId);
}
