package com.github.tomaszgryczka.convey.chat;

import com.github.tomaszgryczka.convey.chat.message.ChatMessage;
import com.github.tomaszgryczka.convey.chat.message.ChatMessageService;
import com.github.tomaszgryczka.convey.chat.message.MessageNotification;
import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ChatRoomService chatRoomService;

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    private void processMessage(ChatMessage chatMessage) {

        String chatId = chatRoomService.getChatId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId());

        chatMessage.setChatId(chatId);

        chatMessageService.save(chatMessage);

        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "queue/messages",
                new MessageNotification(
                        chatMessage.getId(),
                        chatMessage.getSenderId(),
                        chatMessage.getRecipientName()
                ));

    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public List<ChatMessage> findChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return chatMessageService.findChatMessages(senderId, recipientId);
    }

    @GetMapping("/messages/{id}")
    public ChatMessage findChatMessage(@PathVariable Long id) {

        return chatMessageService.findById(id);
    }


    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public Long countNewMessages(@PathVariable String senderId,
                                 @PathVariable String recipientId) {

        return chatMessageService.countNewMessages(senderId, recipientId);
    }
}
