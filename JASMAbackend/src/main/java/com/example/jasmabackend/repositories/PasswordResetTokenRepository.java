package com.example.jasmabackend.repositories;


import com.example.jasmabackend.entities.token.PasswordResetToken;
import com.example.jasmabackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends
    JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    PasswordResetToken findAllByUser(User user);

}
