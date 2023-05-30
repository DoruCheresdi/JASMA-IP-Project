package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.authority.Authority;
import com.example.jasmabackend.entities.token.PasswordResetToken;
import com.example.jasmabackend.entities.friendRequest.FriendRequest;
import com.example.jasmabackend.entities.friendship.Friendship;
import com.example.jasmabackend.entities.like.Like;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.entities.user.UserDTO;
import com.example.jasmabackend.exceptions.UserEmailNotUniqueException;
import com.example.jasmabackend.repositories.*;
import com.example.jasmabackend.service.friendship.FriendshipService;
import com.example.jasmabackend.service.post.PostService;
import com.example.jasmabackend.service.user.UserService;
import com.example.jasmabackend.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final FriendRequestRepository friendRequestRepository;

    private final FriendshipRepository friendshipRepository;

    private final FriendshipService friendshipService;

    private final EmailService emailService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserMessageRepository userMessageRepository;

    @RequestMapping("/devapi/authenticated")
    public Principal user(Principal user) {

        return user;
    }

    @RequestMapping(value = "/devapi/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User user) throws UserEmailNotUniqueException {

        return userService.registerUser(user);
    }

    @GetMapping("/devapi/users")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/devapi/users")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @PostMapping("/devapi/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> json,
         HttpServletRequest request) {

        String newPassword = json.get("newPassword");
        String userEmail = json.get("userEmail");

        User user = userRepository.findByEmail(userEmail).get();
        user.setPassword(newPassword);
        userService.encodePassword(user);

        userRepository.save(user);

        // Generați și salvați tokenul
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.generateToken();
        // Setați o expirare pentru token: 30 de minute
        long currentTimeInMillis = new Date().getTime();
        long expiryTimeInMillis = currentTimeInMillis + (30 * 60 * 1000);
        Date expiryDate = new Date(expiryTimeInMillis);
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetTokenRepository.save(passwordResetToken);

        // Trimiteți email-ul de verificare
        String resetPasswordUrl = "http://localhost:8080/changepassword?token=" + passwordResetToken.getToken();
        String subject = "Verificati schimbarea parolei";
        String message = "Accesati urmatorul link pentru a verifica si finaliza schimbarea parolei: " + resetPasswordUrl;
        emailService.sendEmail(user.getEmail(), subject, message);
        String referer = request.getHeader("Referer");
        String redirectUrl = referer + "?email=" + userEmail + "&password=" + newPassword;

        return ResponseEntity.ok("Procesul de schimbare a parolei a fost inițiat." +
            "Vă rugăm să verificați adresa de email pentru instrucțiuni suplimentare.");
    }

    @GetMapping("/changepassword")
    public String verifyPasswordResetToken(@RequestParam("token") String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        Date date = passwordResetToken.getExpirationDateTime();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime expirationDateTime = LocalDateTime.ofInstant(instant, zoneId);
        if (expirationDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expired");
        }

        return "http://localhost:8080/feed";
    }

    @PostMapping("/devapi/deleteuser")
    public void deleteAccount(@RequestBody String email) {

        // get user that made the request:
        User user = userRepository.findByEmail(email).get();

        user.getAuthorities().forEach(authority -> {
            authority.getUsers().remove(user);
            Set<Authority> authorities = user.getAuthorities();
            authorities.remove(authority);
            user.setAuthorities(authorities);
        });

        friendshipRepository.findAll().forEach(friendship -> {
            if (friendship.getReceiver().equals(user) || friendship.getSender().equals(user)) {
                friendshipRepository.delete(friendship);
            }
        });

        friendRequestRepository.findAll().forEach(friendRequest -> {
            if (friendRequest.getReceiver().equals(user) || friendRequest.getSender().equals(user)) {
                friendRequestRepository.delete(friendRequest);
            }
        });

        user.getPosts().forEach(post -> {
                postService.deletePost(post);
            }
        );

        user.getComments().forEach(comment -> {
            commentRepository.delete(comment);
        });

        // delete messages:
        userMessageRepository.findAll().stream()
            .filter(userMessage -> userMessage.getReceiver().getEmail().equals(user.getEmail())
                || userMessage.getSender().getEmail().equals(user.getEmail())).forEach(userMessage -> userMessageRepository.delete(userMessage));

        userRepository.delete(user);
    }

    @GetMapping("/devapi/user/searchbyname")
    public List<UserDTO> searchByName(@RequestParam String userName, Authentication authentication) {

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findByEmail(userDetails.getUsername()).get();

        List<User> users = userRepository.findAllByNameContaining(userName);

        List<UserDTO> userDTOS = users.stream().map(user -> {
            return userService.getDTO(user, loggedUser);
        }).toList();

        return userDTOS;
    }

    @GetMapping("devapi/user/details")
    public UserDTO getUserDetails(@RequestParam String email, Authentication authentication) {

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findByEmail(userDetails.getUsername()).get();

        User user = userRepository.findByEmail(email).get();

        return userService.getDTO(user, loggedUser);
    }

    @GetMapping("/devapi/user/friends")
    public List<UserDTO> getFriendsOfUser(Authentication authentication) {

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findByEmail(userDetails.getUsername()).get();

        List<User> users = userRepository.findAll().stream().filter(user -> {
            return friendshipService.areFriends(user, loggedUser);
        }).toList();

        List<UserDTO> userDTOS = users.stream().map(user -> {
            return userService.getDTO(user, loggedUser);
        }).toList();

        return userDTOS;
    }

    @PostMapping("/devapi/user/description")
    public ResponseEntity changeDescription(@RequestBody String newDescription, Authentication authentication) {

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();


        user.setDescription(newDescription);
        userRepository.save(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
