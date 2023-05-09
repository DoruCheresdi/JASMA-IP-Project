package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.comment.Comment;
import com.example.jasmabackend.entities.comment.CommentDTO;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.CommentRepository;
import com.example.jasmabackend.repositories.PostRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.utils.UtilsMisc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;



    @PostMapping("/devapi/comment")
    // map: 1. postTitle  2. comment
    public ResponseEntity addComment(@RequestBody CommentDTO commentDto,
                                     Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        Post post = postRepository.findByTitle(commentDto.getPostTitle()).get();

        commentDto.getComment().setUser(user);
        commentDto.getComment().setPost(post);
        commentRepository.save(commentDto.getComment());
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
