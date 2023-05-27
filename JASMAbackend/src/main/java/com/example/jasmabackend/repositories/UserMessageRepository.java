package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.userMessage.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
}
