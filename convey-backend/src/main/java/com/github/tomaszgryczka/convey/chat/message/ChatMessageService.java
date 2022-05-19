package com.github.tomaszgryczka.convey.chat.message;

import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomService chatRoomService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(ChatMessage chatMessage) {
        String chatId = chatRoomService.getChatId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId());

        chatMessage.setChatId(chatId);

        save(chatMessage);

        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "queue/messages",
                new MessageNotification(
                        chatMessage.getId(),
                        chatMessage.getSenderId(),
                        chatMessage.getSenderName()
                ));
    }

    public void save(ChatMessage chatMessage) {
        chatMessage.setMessageStatus(MessageStatus.RECEIVED);
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {

        String chatId = chatRoomService.getChatId(senderId, recipientId);

        List<ChatMessage> messages = chatMessageRepository.findAllByChatId(chatId);

        if (!messages.isEmpty()) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public Long countNewMessages(String senderId, String recipientId) {
        String messageStatus = MessageStatus.RECEIVED.name();

        return chatMessageRepository.countBySenderIdAndRecipientIdAndMessageStatus(senderId, recipientId, messageStatus);
    }

    public ChatMessage findById(Long id) {
        return chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setMessageStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Message " + id + " not found."));

    }

    private void updateStatuses(String senderId, String recipientId, MessageStatus messageStatus) {
        chatMessageRepository.setMessageStatusBySenderIdAndRecipientId(senderId, recipientId, messageStatus.name());
    }
}
