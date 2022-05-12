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

        System.out.println(chatRoom.isPresent());

        if (chatRoom.isPresent()) {
            System.out.println(chatRoom.get().getChatId());
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

            System.out.println(chatId);

            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);

            return chatId;
        }
    }
}
