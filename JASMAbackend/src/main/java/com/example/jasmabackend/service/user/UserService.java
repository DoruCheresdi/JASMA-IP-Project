package com.example.jasmabackend.service.user;

import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.entities.user.UserDTO;
import com.example.jasmabackend.exceptions.UserEmailNotUniqueException;
import com.example.jasmabackend.repositories.FriendRequestRepository;
import com.example.jasmabackend.repositories.FriendshipRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.service.friendship.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final FriendshipService friendshipService;

    private final FriendRequestRepository friendRequestRepository;

    public void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public User registerUser(User user) throws UserEmailNotUniqueException {
        encodePassword(user);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailNotUniqueException(user.getEmail());
        }
        return userRepository.save(user);
    }

    public String getUserImagePath(User user) {
        return "user-photos/" + user.getEmail() + "/" + user.getImageName();
    }

    public UserDTO getDTO(User user, User loggedUser) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setFriend(friendshipService.areFriends(user, loggedUser));
        dto.setHasSentFriendRequest(friendRequestRepository.findFriendRequestBySenderAndReceiver(user, loggedUser).isPresent());
        dto.setHasReceivedFriendRequest(friendRequestRepository.findFriendRequestBySenderAndReceiver(loggedUser, user).isPresent());
        dto.setImageURL(getUserImagePath(user));

        return dto;
    }
}
