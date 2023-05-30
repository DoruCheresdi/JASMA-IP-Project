package com.example.jasmabackend.entities.friendship;

import com.example.jasmabackend.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friendships")
public class Friendship {

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
