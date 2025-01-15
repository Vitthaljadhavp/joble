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


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // Automatically inject BCryptPasswordEncoder
 public boolean checkPasswordMatch(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Log the raw password and stored hashed password for debugging
            System.out.println("Stored password hash: " + user.getPassword()); // The hashed password in the database
            System.out.println("Raw password: " + rawPassword); // The raw password input by the user

            boolean passwordMatches = bCryptPasswordEncoder.matches(rawPassword, user.getPassword());
            System.out.println("Password comparison result: " + passwordMatches);  // Debug log
            return passwordMatches;
        }
        return false;
    }


    public User registerUser(User user) {
        // Encode the password before saving the user
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }



    
}
