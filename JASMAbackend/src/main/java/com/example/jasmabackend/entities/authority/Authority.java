package com.example.jasmabackend.entities.authority;

import com.example.jasmabackend.entities.user.User;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    public Authority(String name) {
        this.name = name;
    }

    public Authority() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "users_authorities",
            joinColumns = { @JoinColumn(name = "authorities_id") },
            inverseJoinColumns = { @JoinColumn(name = "users_id") }
    )
    private Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Authority other = (Authority) obj;
        return Objects.equals(name, other.getName());
    }
}
