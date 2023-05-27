package com.example.jasmabackend.service.post;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.post.PostFeedDTO;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.*;
import com.example.jasmabackend.utils.UtilsMisc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final LikeRepository likeRepository;

    private final ShareRepository shareRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public void deletePost(Post post) {
        post.getShares().forEach(share -> {
            shareRepository.delete(share);
        });
        post.getLikes().forEach(like -> {
            likeRepository.delete(like);
        });
        post.getComments().forEach(comment -> {
            commentRepository.delete(comment);
        });

        postRepository.delete(post);
    }

    public String getPostImagePath(Post post) {
        return "post-photos/" + post.getTitle() + "/" + post.getImageName();
    }

    public String getPostVideoPath(Post post) {
        return "post-videos/" + post.getTitle() + "/" + post.getVideoName();
    }

    public PostFeedDTO createPostDTO(Post post, User currentUser) {
        PostFeedDTO dto = new PostFeedDTO();
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setImageUrl(getPostImagePath(post));
        dto.setVideoUrl(getPostVideoPath(post));
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
