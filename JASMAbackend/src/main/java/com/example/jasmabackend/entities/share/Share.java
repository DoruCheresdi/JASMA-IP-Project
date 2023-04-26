package com.example.jasmabackend.entities.share;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shares")
public class Share {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
}
