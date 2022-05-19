package com.github.tomaszgryczka.convey.chat.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatId(String ChatId);

    Long countBySenderIdAndRecipientIdAndMessageStatus(String senderId, String recipientId, String messageStatus);

    Optional<ChatMessage> findById(Long id);


    @Transactional
    @Modifying
    @Query("update Chat_Message set message_status = ?3 where sender_id = ?1 and recipient_id = ?2")
    void setMessageStatusBySenderIdAndRecipientId(String senderId, String recipientId, String messageStatus);
}
