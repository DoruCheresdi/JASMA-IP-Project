package com.example.jasmabackend.entities.friendRequest;

import com.example.jasmabackend.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friendRequests")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private User sender;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private User receiver;
}
