package com.example.jasmabackend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(unique=true)
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
