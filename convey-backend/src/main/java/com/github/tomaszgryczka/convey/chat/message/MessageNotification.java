package com.github.tomaszgryczka.convey.chat.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageNotification {

    private Long id;

    private String senderId;

    private String senderName;
}
