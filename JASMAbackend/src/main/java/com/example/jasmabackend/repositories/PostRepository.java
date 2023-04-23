package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);
}
