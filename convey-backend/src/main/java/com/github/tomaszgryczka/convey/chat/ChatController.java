package com.github.tomaszgryczka.convey.chat;

import com.github.tomaszgryczka.convey.chat.message.ChatMessage;
import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    private ChatRoomService chatRoomService;

    private void processMessage(@Payload ChatMessage chatMessage) {
        String chatId = chatRoomService.getChatId(
                chatMessage.getSenderId(),
                chatMessage.getRecipentId(),
                true);
    }
}
