package com.example.jasmabackend.service.messaging;

import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.entities.userMessage.ConversationDTO;
import com.example.jasmabackend.entities.userMessage.UserMessage;
import com.example.jasmabackend.entities.userMessage.UserMessageDTO;
import com.example.jasmabackend.repositories.FriendshipRepository;
import com.example.jasmabackend.repositories.UserMessageRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.service.friendship.FriendshipService;
import com.example.jasmabackend.utils.UtilsMisc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMessageService {

    private final UserMessageRepository userMessageRepository;

    private final FriendshipService friendshipService;

    private final UserRepository userRepository;

    public void createMessage(User sender, User receiver, String content) {
        UserMessage userMessage = new UserMessage();
        userMessage.setSender(sender);
        userMessage.setReceiver(receiver);
        userMessage.setContent(content);
        userMessage.setCreatedAt(UtilsMisc.getTimestamp());

        userMessageRepository.save(userMessage);
    }

    public List<UserMessage> getMessagesForUsers(String user1Email, String user2Email) {
        return userMessageRepository.findAll().stream()
            .filter(userMessage -> (userMessage.getSender().getEmail().equals(user1Email) && userMessage.getReceiver().getEmail().equals(user2Email))
                || (userMessage.getSender().getEmail().equals(user2Email) && userMessage.getReceiver().getEmail().equals(user1Email))
            ).toList();
    }

    public List<UserMessageDTO> getMessageDtosForUsers(String user1Email, String user2Email) {
        List<UserMessageDTO> dtos;

        dtos = getMessagesForUsers(user1Email, user2Email).stream().map(userMessage -> new UserMessageDTO(userMessage.getContent(),
                userMessage.getCreatedAt(), userMessage.getSender().getEmail(), userMessage.getReceiver().getEmail()))
            .toList();

        return dtos;
    }

    /**
     * Get all the conversations the user has with their friends
     * @param user
     * @return
     */
    public List<ConversationDTO> getConversationDtosForUser(User user) {
        List<ConversationDTO> dtos = new ArrayList<>();

        // get friends:
        List<User> friends = userRepository.findAll().stream().filter(
            friend -> friendshipService.areFriends(user, friend)
        ).toList();

        // check each friend for messages, only add a conversation if there is a message:
        for (User friend : friends) {
            List<UserMessage> messages = getMessagesForUsers(friend.getEmail(), user.getEmail()).stream().sorted(
                Comparator.comparing(UserMessage::getCreatedAt)
            ).toList();
            if (messages.size() > 0) {
                UserMessage lastMessage = messages.get(messages.size() - 1);
                dtos.add(new ConversationDTO(lastMessage.getContent(), lastMessage.getSender().getEmail(), lastMessage.getCreatedAt(), friend.getEmail(), user.getEmail()));
            }
        }

        return dtos;
    }
}
