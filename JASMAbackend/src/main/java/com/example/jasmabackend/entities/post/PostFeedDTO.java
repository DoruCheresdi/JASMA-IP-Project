package com.example.jasmabackend.entities.post;

import com.example.jasmabackend.utils.UtilsMisc;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class PostFeedDTO {

    @Column(unique=true)
    private String title;

    private String description;

    private String imageUrl;

    private String videoUrl;

    private String sinceCreatedString;

    private String isLikedByCurrentUser;

    private Integer numberLikes;

    private String isSharedByCurrentUser;

    private Integer numberShares;
}
