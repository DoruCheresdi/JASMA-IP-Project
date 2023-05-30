package com.example.jasmabackend.entities.user;

import com.example.jasmabackend.entities.authority.Authority;
import com.example.jasmabackend.entities.comment.Comment;
import com.example.jasmabackend.entities.like.Like;
import com.example.jasmabackend.entities.post.Post;
import com.example.jasmabackend.entities.share.Share;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String email;

    private String password;

    private String name;

    private String description;

    private String imageName;

    /**
     * Take care with the cascade attribute, it may bring entities back from the dead:
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
        orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
        orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Like> likes = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
        orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Share> shares = new ArrayList<>();


    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
