package com.example.jasmabackend;

import com.example.jasmabackend.entities.authority.Authority;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.AuthorityRepository;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.config.web.server.ServerAnonymousDsl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // create admin at startup:
        if (userRepository.findByEmail("admin").isEmpty()) {
            User admin = new User();
            admin.setPassword("admin");
            userService.encodePassword(admin);
            admin.setEmail("admin");
            admin.setName("admin");

            if (authorityRepository.findByName("ROLE_ADMIN").isPresent()) {
                Set<Authority> authorities = new HashSet<>();
                Authority authority = authorityRepository.findByName("ROLE_ADMIN").get();
                authorities.add(authority);
                admin.setAuthorities(authorities);
                authority.getUsers().add(admin);
            } else {
                Authority adminAuth = new Authority("ROLE_ADMIN");
                Set<Authority> authorities = admin.getAuthorities();
                authorities.add(adminAuth);
                admin.setAuthorities(authorities);
                adminAuth.getUsers().add(admin);
            }

            userRepository.save(admin);
        } else {
            User admin = userRepository.findByEmail("admin").get();
            if (admin.getAuthorities().stream()
                .noneMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
                if (authorityRepository.findByName("ROLE_ADMIN").isPresent()) {
                    Set<Authority> authorities = admin.getAuthorities();
                    Authority authority = authorityRepository.findByName("ROLE_ADMIN").get();
                    authorities.add(authority);
                    admin.setAuthorities(authorities);
                    authority.getUsers().add(admin);
                } else {
                    Authority adminAuth = new Authority("ROLE_ADMIN");
                    Set<Authority> authorities = admin.getAuthorities();
                    authorities.add(adminAuth);
                    admin.setAuthorities(authorities);
                    adminAuth.getUsers().add(admin);
                }
                userRepository.save(admin);
            }
        }
    }
}
