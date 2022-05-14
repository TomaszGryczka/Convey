package com.github.tomaszgryczka.convey.chat.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public String getChatId(String senderId, String recipientId) {

        return (chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId))
                .orElseGet(() -> {
                    return makeNewChatRoom(senderId, recipientId);
                }).getChatId();
    }

    private ChatRoom makeNewChatRoom(String senderId, String recipientId) {
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

        System.out.println("tak");

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return senderRecipient;
    }


}
