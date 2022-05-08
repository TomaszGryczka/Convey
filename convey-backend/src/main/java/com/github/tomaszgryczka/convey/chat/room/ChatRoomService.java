package com.github.tomaszgryczka.convey.chat.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public String getChatId(String senderId, String recipientId) {

        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);

        if (chatRoom.isPresent()) {
            return chatRoom.get().getChatId();
        } else {
            String chatId = String.format("%s_%s", senderId, recipientId);

            ChatRoom senderRecipient = ChatRoom
                    .builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();

            ChatRoom recipientSender = ChatRoom
                    .builder()
                    .chatId(chatId)
                    .senderId(recipientId)
                    .recipientId(senderId)
                    .build();

            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);

            return chatId;
        }
    }
}
