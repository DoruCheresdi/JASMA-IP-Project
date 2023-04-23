package com.example.jasmabackend.entities.post;

import com.example.jasmabackend.entities.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private String videoUrl;

    @ManyToOne
    private User user;
}
