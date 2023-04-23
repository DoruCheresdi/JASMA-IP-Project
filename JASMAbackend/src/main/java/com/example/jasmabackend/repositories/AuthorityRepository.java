package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.authority.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;


public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Optional<Authority> findByName(String name);

    /**
     * Loads all authorities of a user
     * @param email email of the user
     * @return list of all authorities of the user
     */
    @QueryHints(value = { @QueryHint(name = "QueryHints.PASS_DISTINCT_THROUGH", value = "false")})
    @Query("select distinct a from Authority a join a.users u where u.email = :email")
    List<Authority> findAllByUserEmail(String email);
}
