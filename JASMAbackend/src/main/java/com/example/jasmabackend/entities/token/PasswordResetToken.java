package com.example.jasmabackend.entities.token;

import com.example.jasmabackend.entities.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void generateToken() {
        token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, EXPIRATION);
        expiryDate = calendar.getTime();
    }

    public String getToken() {
        return token;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getExpirationDateTime() {
        return expiryDate;
    }
}
