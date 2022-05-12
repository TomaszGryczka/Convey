package com.github.tomaszgryczka.convey.chat.message;

import com.github.tomaszgryczka.convey.chat.room.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomService chatRoomService;

    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {

        String chatId = chatRoomService.getChatId(senderId, recipientId);

        List<ChatMessage> messages = chatMessageRepository.findAllByChatId(chatId);

        return messages;
    }

    public Long countNewMessages(String senderId, String recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientId(senderId, recipientId);
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
}
