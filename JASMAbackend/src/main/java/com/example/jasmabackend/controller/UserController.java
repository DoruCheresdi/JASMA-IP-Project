package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.friendRequest.FriendRequest;
import com.example.jasmabackend.entities.friendship.Friendship;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.entities.user.UserDTO;
import com.example.jasmabackend.exceptions.UserEmailNotUniqueException;
import com.example.jasmabackend.repositories.FriendRequestRepository;
import com.example.jasmabackend.repositories.FriendshipRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.service.friendship.FriendshipService;
import com.example.jasmabackend.service.user.UserService;
import com.example.jasmabackend.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final FriendRequestRepository friendRequestRepository;

    private final FriendshipRepository friendshipRepository;

    private final FriendshipService friendshipService;

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
    public void changePassword(@RequestBody Map<String, String> json) {

        String newPassword = json.get("newPassword");
        String userEmail = json.get("userEmail");

        User user = userRepository.findByEmail(userEmail).get();
        user.setPassword(newPassword);
        userService.encodePassword(user);

        userRepository.save(user);
    }

    @PostMapping("/devapi/deleteuser")
    public void deleteOwnAccount(Authentication authentication) {

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

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
}
