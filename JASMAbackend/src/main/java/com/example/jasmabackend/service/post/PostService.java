package com.example.jasmabackend.service.post;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.post.PostFeedDTO;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.FriendshipRepository;
import com.example.jasmabackend.repositories.LikeRepository;
import com.example.jasmabackend.repositories.ShareRepository;
import com.example.jasmabackend.utils.UtilsMisc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final LikeRepository likeRepository;

    private final ShareRepository shareRepository;

    public PostFeedDTO createPostDTO(Post post, User currentUser) {
        PostFeedDTO dto = new PostFeedDTO();
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setImageUrl(post.getImageUrl());
        dto.setVideoUrl(post.getVideoUrl());
        dto.setSinceCreatedString(UtilsMisc.getSinceCreatedString(post.getCreatedAt()));
        dto.setAuthorEmail(post.getUser().getEmail());

        dto.setNumberLikes(likeRepository.findAllByPost(post).size());
        // the post is liked by the current user:
        if (likeRepository.findAllByUser(currentUser).stream()
            .anyMatch(like -> {
                return like.getPost().equals(post);
            })) {
            dto.setIsLikedByCurrentUser("true");
        } else {
            dto.setIsLikedByCurrentUser("false");
        }

        dto.setNumberShares(shareRepository.findAllByPost(post).size());
        // the post is liked by the current user:
        if (shareRepository.findAllByUser(currentUser).stream()
            .anyMatch(share -> {
                return share.getPost().equals(post);
            })) {
            dto.setIsSharedByCurrentUser("true");
        } else {
            dto.setIsSharedByCurrentUser("false");
        }

        return dto;
    }
}
