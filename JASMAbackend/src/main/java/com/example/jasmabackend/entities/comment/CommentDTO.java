package com.example.jasmabackend.entities.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private String postTitle;
    private String authorEmail;
    private String text;
    private String sinceCreatedString;
}
