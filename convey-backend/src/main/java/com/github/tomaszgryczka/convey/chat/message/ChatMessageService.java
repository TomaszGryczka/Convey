package com.github.tomaszgryczka.convey.chat.message;

import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {

        String chatId = chatRoomService.getChatId(senderId, recipientId);

        // List<ChatMessage> messages = //TODO

        return null;
    }
}
