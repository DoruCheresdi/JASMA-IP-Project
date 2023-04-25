package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.friendRequest.FriendRequest;
import com.example.jasmabackend.entities.like.Like;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.LikeRepository;
import com.example.jasmabackend.repositories.PostRepository;
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
public class LikeController {

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    @PostMapping("/devapi/like")
    public ResponseEntity addLike(@RequestBody String postTitle, Authentication authentication) {
        // IMPORTANT: pentru endpointurile care au doar un string, nu se mai trimite JSON, ci doar acel string

        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();


        if (likeRepository.findByUser(user).isPresent()) {
            // request is already sent, should return error, to implement later:
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        Post post = postRepository.findByTitle(postTitle).get();

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
