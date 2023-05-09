package com.example.jasmabackend.entities.comment;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @Column(name="content")
    private String content;
}
