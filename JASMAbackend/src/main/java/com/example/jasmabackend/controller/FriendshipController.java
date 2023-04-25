package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.friendRequest.FriendRequest;
import com.example.jasmabackend.entities.friendship.Friendship;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.FriendRequestRepository;
import com.example.jasmabackend.repositories.FriendshipRepository;
import com.example.jasmabackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendshipController {

    private final UserRepository userRepository;

    private final FriendRequestRepository friendRequestRepository;

    private final FriendshipRepository friendshipRepository;

    @PostMapping("/devapi/friendRequest")
    public ResponseEntity addFriendRequest(@RequestBody String receiverEmail, Authentication authentication) {
        // IMPORTANT: pentru endpointurile care au doar un string, nu se mai trimite JSON, ci doar acel string

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User sender = userRepository.findByEmail(userDetails.getUsername()).get();

        User receiver = userRepository.findByEmail(receiverEmail).get();

        if (friendRequestRepository.findFriendRequestBySenderAndReceiver(sender, receiver).isPresent()) {
            // request is already sent, should return error, to implement later:
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setReceiver(receiver);
        friendRequest.setSender(sender);
        friendRequestRepository.save(friendRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Get a list of all users that sent a friend request to this user
     * @param authentication
     * @return
     */
    @GetMapping("/devapi/friendRequests")
    public List<User> getFriendRequestsSenders(Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User receiver = userRepository.findByEmail(userDetails.getUsername()).get();

        List<User> sendersList = new ArrayList<>();
        friendRequestRepository.findAllByReceiver(receiver).forEach(friendRequest -> {
            sendersList.add(friendRequest.getSender());
        });
        return sendersList;
    }

    @PostMapping("/devapi/friendRequest/accept")
    public ResponseEntity acceptFriendRequest(@RequestBody String senderEmail, Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User receiver = userRepository.findByEmail(userDetails.getUsername()).get();

        User sender = userRepository.findByEmail(senderEmail).get();

        FriendRequest friendRequest = friendRequestRepository.findFriendRequestBySenderAndReceiver(sender, receiver).get();
        // delete request:
        friendRequestRepository.delete(friendRequest);

        // add friendship:
        Friendship friendship = new Friendship();
        friendship.setReceiver(receiver);
        friendship.setSender(sender);
        friendshipRepository.save(friendship);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/devapi/friendRequest/reject")
    public ResponseEntity rejectFriendRequest(@RequestBody String senderEmail, Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User receiver = userRepository.findByEmail(userDetails.getUsername()).get();

        User sender = userRepository.findByEmail(senderEmail).get();

        FriendRequest friendRequest = friendRequestRepository.findFriendRequestBySenderAndReceiver(sender, receiver).get();
        // delete request:
        friendRequestRepository.delete(friendRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Get a list of all users that sent a friend request to this user
     * @param authentication
     * @return
     */
    @GetMapping("/devapi/friends")
    public List<User> getFriendsOfUser(Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        List<Friendship> friendships = friendshipRepository.findAll();
        List<User> friendsList = new ArrayList<>();
        friendships.forEach(friendship -> {
            if (friendship.getSender().equals(user)) {
                friendsList.add(friendship.getReceiver());
            } else if (friendship.getReceiver().equals(user)) {
                friendsList.add(friendship.getSender());
            }
        });

        return friendsList;
    }
}
