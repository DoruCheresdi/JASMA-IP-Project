package com.example.jasmabackend.entities.userMessage;

import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.utils.UtilsMisc;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class UserMessageDTO {

    private String content;

    private Timestamp createdAt;

    private String createdAtString;

    private String senderEmail;

    private String receiverEmail;

    public UserMessageDTO(String content, Timestamp createdAt, String senderEmail, String receiverEmail) {
        this.content = content;
        this.createdAt = createdAt;
        this.createdAtString = UtilsMisc.getSinceCreatedString(createdAt);
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
    }
}
