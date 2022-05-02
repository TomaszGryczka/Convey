package com.github.tomaszgryczka.convey.chat.room;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
@Builder
public class ChatRoom {

    private String id;

    private String chatId;

    private String senderId;

    private String recipientId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
