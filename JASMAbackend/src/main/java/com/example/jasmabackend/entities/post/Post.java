package com.example.jasmabackend.entities.post;

import com.example.jasmabackend.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String title;

    private String description;

    private String imageUrl;

    private String videoUrl;

    private Timestamp createdAt;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public String getSinceCreatedString() {
        // get time since course has been created in pretty format:
        PrettyTime t = new PrettyTime(new Date(System.currentTimeMillis()));
        return t.format(new Date(createdAt.getTime()));
    }

    public void updateCourseTimestamp() {
        Timestamp reviewCreatedAt = new Timestamp(System.currentTimeMillis());
        setCreatedAt(reviewCreatedAt);
    }
}
