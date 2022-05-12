package com.github.tomaszgryczka.convey.chat.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatId(String ChatId);

    Long countBySenderIdAndRecipientId(String senderId, String recipientId);

    Optional<ChatMessage> findById(Long id);
}
