package com.joble.joble.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joble.joble.model.User;
import com.joble.joble.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;


    // @Autowired
    // private BCryptPasswordEncoder passwordEncoder; // Automatically inject BCryptPasswordEncoder

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injecting the PasswordEncoder

    public User registerUser(User user) {
        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    
}
