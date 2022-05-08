package com.github.tomaszgryczka.convey.chat.message;

public class MessageNotification {

    private String id;

    private String senderId;

    private String senderName;

    public MessageNotification(String id, String senderId, String senderName) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
    }
}
