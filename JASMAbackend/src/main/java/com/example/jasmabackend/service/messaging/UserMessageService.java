package com.example.jasmabackend.service.messaging;

import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.entities.userMessage.UserMessage;
import com.example.jasmabackend.repositories.UserMessageRepository;
import com.example.jasmabackend.utils.UtilsMisc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMessageService {

    private final UserMessageRepository userMessageRepository;

    public void createMessage(User sender, User receiver, String content) {
        UserMessage userMessage = new UserMessage();
        userMessage.setSender(sender);
        userMessage.setReceiver(receiver);
        userMessage.setContent(content);
        userMessage.setCreatedAt(UtilsMisc.getTimestamp());

        userMessageRepository.save(userMessage);
    }
}
