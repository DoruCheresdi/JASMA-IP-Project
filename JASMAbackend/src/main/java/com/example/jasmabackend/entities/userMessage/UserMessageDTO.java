package com.example.jasmabackend.entities.userMessage;

import com.example.jasmabackend.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class UserMessageDTO {

    private Long id;

    private String content;

    private Timestamp createdAt;

    private String senderEmail;

    private String receiverEmail;
}
