package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.like.Like;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByUser(User user);

    List<Like> findAllByPost(Post post);

    Optional<Like> findByUserAndPost(User user, Post post);
}
