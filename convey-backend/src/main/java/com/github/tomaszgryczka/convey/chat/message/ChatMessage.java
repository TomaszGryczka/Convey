package com.github.tomaszgryczka.convey.chat.message;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Chat_Message")
@Table(name = "chat_message")
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    private String senderId;

    private String recipientId;

    private String senderName;

    private String recipientName;

    private String content;

    private Date timestamp;

    private String messageStatus;

    public void setMessageStatus(MessageStatus messageStatus) {

        this.messageStatus = messageStatus.name();
    }
}
