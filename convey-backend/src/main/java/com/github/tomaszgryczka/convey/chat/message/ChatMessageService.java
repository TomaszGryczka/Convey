package com.github.tomaszgryczka.convey.chat.message;

import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

        List<ChatMessage> messages = chatMessageRepository.findAllById(chatId);

        return messages;
    }
}
