package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.PostRepository;
import com.example.jasmabackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @GetMapping("/devapi/posts_user")
    public List<Post> getPostsByUser(@RequestParam String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        return (List<Post>) postRepository.findAllByUser(user);
    }

    @PostMapping("/devapi/post")
    public void addPost(@RequestBody Post post, Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        post.setUser(user);
        postRepository.save(post);
    }

    @DeleteMapping("/devapi/post")
    public void deletePost(@RequestParam String title) {
        Post post = postRepository.findByTitle(title).get();

        postRepository.delete(post);
    }
}
