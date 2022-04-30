package com.github.tomaszgryczka.convey.chat.message;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
public class ChatMessage {

    @Id
    private String id;

    private String chatId;

    private String senderId;

    private String recipentId;

    private String senderName;

    private String recipendName;

    private String content;

    private Date timestamp;
}
