package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.authority.Authority;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.post.PostFeedDTO;
import com.example.jasmabackend.entities.share.Share;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.AuthorityRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.utils.UtilsMisc;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    @PostMapping("/devapi/make_admin")
    public void makeAdmin(@RequestBody String email) {
        // get user that made the request:
        User user = userRepository.findByEmail(email).get();

        Authority adminAuth = authorityRepository.findByName("ROLE_ADMIN").get();
        user.getAuthorities().add(adminAuth);
        adminAuth.getUsers().add(user);
        userRepository.save(user);
        authorityRepository.save(adminAuth);
    }


    @GetMapping("/devapi/is_admin")
    public boolean isAdmin(Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findByEmail(userDetails.getUsername()).get();

//        User user = userRepository.findByEmail(email).get();

        Authority adminAuth = authorityRepository.findByName("ROLE_ADMIN").get();
        return loggedUser.getAuthorities().contains(adminAuth);
    }
}
