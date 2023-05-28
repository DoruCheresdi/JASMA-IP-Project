package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.friendRequest.FriendRequest;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.entities.userMessage.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

    List<UserMessage> findAllBySenderAndReceiver(User sender, User receiver);
}
