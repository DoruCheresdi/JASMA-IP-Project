package com.example.jasmabackend.service.friendship;

import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public boolean areFriends(User user1, User user2) {
        if (friendshipRepository.findFriendshipBySenderAndReceiver(user1, user2).isPresent())
            return true;
        if (friendshipRepository.findFriendshipBySenderAndReceiver(user2, user1).isPresent())
            return true;
        return false;
    }
}
