package com.example.jasmabackend.service.user;

import com.example.jasmabackend.entities.user.CustomUserDetails;
import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.AuthorityRepository;
import com.example.jasmabackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();
        user.setAuthorities(new HashSet<>(authorityRepository.findAllByUserEmail(user.getEmail())));
        return new CustomUserDetails(user);
    }
}
