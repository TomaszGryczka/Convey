package com.github.tomaszgryczka.convey.chat;

import com.github.tomaszgryczka.convey.chat.message.ChatMessage;
import com.github.tomaszgryczka.convey.chat.message.ChatMessageService;
import com.github.tomaszgryczka.convey.chat.message.MessageNotification;
import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ChatRoomService chatRoomService;

    private final ChatMessageService chatMessageService;

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
                new MessageNotification(
                        chatMessage.getChatId(),
                        chatMessage.getSenderId(),
                        chatMessage.getRecipientId()
                ));

    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }


    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<?> countNewMessages(@PathVariable String senderId,
                                              @PathVariable String recipientId) {
        return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId));
    }
}
