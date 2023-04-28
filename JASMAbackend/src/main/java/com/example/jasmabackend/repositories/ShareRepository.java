package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.like.Like;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.share.Share;
import com.example.jasmabackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {

    List<Share> findAllByUser(User user);

    List<Share> findAllByPost(Post post);

    Optional<Share> findByUserAndPost(User user, Post post);
}
