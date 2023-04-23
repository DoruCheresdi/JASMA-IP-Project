package com.example.jasmabackend.service.user;

import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.exceptions.UserEmailNotUniqueException;
import com.example.jasmabackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public User registerUser(User user) throws UserEmailNotUniqueException {
        encodePassword(user);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailNotUniqueException(user.getEmail());
        }
        return userRepository.save(user);
    }
}
