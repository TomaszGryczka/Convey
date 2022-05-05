package com.github.tomaszgryczka.convey.chat;

import com.github.tomaszgryczka.convey.chat.message.ChatMessage;
import com.github.tomaszgryczka.convey.chat.message.ChatMessageRepository;
import com.github.tomaszgryczka.convey.chat.message.ChatMessageService;
import com.github.tomaszgryczka.convey.chat.message.MessageNotification;
import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    private void processMessage(@Payload ChatMessage chatMessage) {
        String chatId = chatRoomService.getChatId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId());

        chatMessage.setChatId(chatId);

        chatMessageService.save(chatMessage);

        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "queue/messages",
                new MessageNotification()); //it should be fixed


    }

    @GetMapping("/messages/{senderId}/{recipiendId}")
    public ResponseEntity<?> findChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
