package com.example.jasmabackend.controller.messaging;

import com.example.jasmabackend.entities.comment.CommentDTO;
import com.example.jasmabackend.entities.like.Like;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.entities.userMessage.ConversationDTO;
import com.example.jasmabackend.entities.userMessage.UserMessageDTO;
import com.example.jasmabackend.repositories.UserMessageRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.service.messaging.UserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ConversationController {

    private final UserMessageRepository userMessageRepository;

    private final UserRepository userRepository;

    private final UserMessageService userMessageService;

    /**
     * This endpoint starts a conversation by sending a "Hello there!" message to receiver
     * @param receiverEmail
     * @param authentication
     * @return
     */
    @PostMapping("/devapi/conversation")
    public ResponseEntity startConversation(@RequestBody String receiverEmail, Authentication authentication) {
        // IMPORTANT: pentru endpointurile care au doar un string, nu se mai trimite JSON, ci doar acel string

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findByEmail(userDetails.getUsername()).get();

        User receiver = userRepository.findByEmail(receiverEmail).get();
        userMessageService.createMessage(loggedUser, receiver, "Hello there!");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * This endpoint sends a message to receiver from the currently authenticated user, request body form:
     * {
     *     "receiverEmail" : ...
     *     "content" : ...
     * }
     * @param json
     * @param authentication
     * @return
     */
    @PostMapping("/devapi/message")
    public ResponseEntity sendMessage(@RequestBody Map<String, String> json, Authentication authentication) {

        String receiverEmail = json.get("receiverEmail");
        String content = json.get("content");

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findByEmail(userDetails.getUsername()).get();

        User receiver = userRepository.findByEmail(receiverEmail).get();
        userMessageService.createMessage(loggedUser, receiver, content);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    /**
     * All messages between user1 and user2 in UserMessageDTO list form
     * Get request, parameters in url not in body
     * @param user1Email
     * @param user2Email
     * @return
     */
    @GetMapping("/devapi/messages")
    public List<UserMessageDTO> getMessagesForUsers(@RequestParam String user1Email, @RequestParam String user2Email) {

        return userMessageService.getMessageDtosForUsers(user1Email, user2Email);
    }


    /**
     * Conversations start when a first message is sent, this can be done with startConversation
     * endpoint above or simply by sending a message
     * @return
     */
    @GetMapping("/devapi/conversations")
    public List<ConversationDTO> getConversationDTOsForUser(Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findByEmail(userDetails.getUsername()).get();


        return userMessageService.getConversationDtosForUser(loggedUser);
    }
}
