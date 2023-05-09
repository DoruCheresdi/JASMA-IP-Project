package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.comment.Comment;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUser(User user);

    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUserAndPost(User user, Post post);


}
