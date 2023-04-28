package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.post.PostFeedDTO;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.FriendshipRepository;
import com.example.jasmabackend.repositories.PostRepository;
import com.example.jasmabackend.repositories.ShareRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.service.post.PostService;
import com.example.jasmabackend.utils.UtilsMisc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final FriendshipRepository friendshipRepository;

    private final ShareRepository shareRepository;

    private final PostService postService;

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
        post.setCreatedAt(UtilsMisc.getTimestamp());
        postRepository.save(post);
    }

    @DeleteMapping("/devapi/post")
    public ResponseEntity deletePost(@RequestParam String title) {
        Post post = postRepository.findByTitle(title).get();

        postRepository.delete(post);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Get the posts that should be displayed on the current user's feed
     * @param authentication
     * @return
     */
    @GetMapping("/devapi/feed_posts")
    public List<PostFeedDTO> getFeedPosts(Authentication authentication) {
        // get user that made the request:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        // find out who their friends are:
        List<User> friends = friendshipRepository.findAll().stream().filter(friendship -> {
            return (friendship.getReceiver().equals(user) || friendship.getSender().equals(user));
        }).map(friendship -> {
            if (friendship.getReceiver().equals(user)) return friendship.getSender();
            return friendship.getReceiver();
        }).toList();

        // get all their friends' posts, and the posts shared by their friends:
        Set<Post> postsSet = new HashSet<>();
        friends.forEach(friend -> {
            // add users' own posts:
            postsSet.addAll(postRepository.findAllByUser(friend));
            // add the shared posts too:
            List<Post> sharedPosts = shareRepository.findAll().stream()
                .filter(share -> {
                    return (share.getUser().equals(friend));
                }).map(share -> share.getPost()).toList();
            postsSet.addAll(sharedPosts);
        });

        List<Post> postList = new ArrayList<>();
        postList.addAll(postsSet);

        // remove a user's own posts from feed:
        postList = postList.stream().filter(post -> !post.getUser().equals(user)).toList();

        return postList.stream().map(post -> {
            return postService.createPostDTO(post, user);
        }).toList();
    }
}
