package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.PostRepository;
import com.example.jasmabackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @GetMapping("/posts_user")
    public List<Post> getPostsByUser(@RequestParam String userEmail) {
        return (List<Post>) postRepository.findAll();
    }

    @PostMapping("/post")
    void addPost(@RequestBody Post post) {
        postRepository.save(post);
    }
}
