package com.github.tomaszgryczka.convey.chat.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public String getChatId(String senderId, String recipentId, boolean createIfNotExists) {


        return null;
    }
}
