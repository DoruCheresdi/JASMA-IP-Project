package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.share.Share;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.PostRepository;
import com.example.jasmabackend.repositories.ShareRepository;
import com.example.jasmabackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShareController {

    private final UserRepository userRepository;

    private final ShareRepository shareRepository;

    private final PostRepository postRepository;

    @PostMapping("/devapi/share")
    public ResponseEntity addShare(@RequestBody String postTitle, Authentication authentication) {
        // IMPORTANT: pentru endpointurile care au doar un string, nu se mai trimite JSON, ci doar acel string

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        Post post = postRepository.findByTitle(postTitle).get();

        if (shareRepository.findByUserAndPost(user, post).isPresent()) {
            // request is already sent, should return error, to implement later:
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        Share share = new Share();
        share.setUser(user);
        share.setPost(post);
        shareRepository.save(share);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
