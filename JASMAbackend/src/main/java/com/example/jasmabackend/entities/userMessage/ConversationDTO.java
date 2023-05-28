package com.example.jasmabackend.entities.userMessage;

import com.example.jasmabackend.utils.UtilsMisc;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ConversationDTO {

    private String lastMessageContent;

    private String lastMessageSenderEmail;

    private Timestamp createdAt;

    private String createdAtString;

    private String user1Email;

    private String user2Email;

    public ConversationDTO(String lastMessageContent, String lastMessageSenderEmail, Timestamp createdAt, String user1Email, String user2Email) {
        this.lastMessageContent = lastMessageContent;
        this.lastMessageSenderEmail = lastMessageSenderEmail;
        this.createdAt = createdAt;
        this.createdAtString = UtilsMisc.getSinceCreatedString(createdAt);
        this.user1Email = user1Email;
        this.user2Email = user2Email;
    }
}
