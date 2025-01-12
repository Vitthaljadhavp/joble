package com.joble.joble.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.joble.joble.model.User;
import com.joble.joble.security.JwtTokenProvider;
import com.joble.joble.service.UserService;
import com.joble.joble.dto.LoginRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Check if the email is already registered
        if (userService.findUserByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Email is already taken.");
        }

        // Validate the role (it should be either EMPLOYER or JOB_SEEKER)
        if (!registerRequest.getRole().equalsIgnoreCase("EMPLOYER") && !registerRequest.getRole().equalsIgnoreCase("JOB_SEEKER")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid role. Role should be either 'EMPLOYER' or 'JOB_SEEKER'.");
        }

        // Create new user and save
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());  // Password is plain text
        user.setRole(registerRequest.getRole());

        // Encrypt the password before saving
        userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        // Validate credentials using the checkPasswordMatch method from UserService
        boolean passwordMatches = userService.checkPasswordMatch(loginRequest.getEmail(), loginRequest.getPassword());
        System.out.println("Password matches: " + passwordMatches);  // Debug log


        // If password matches, generate JWT token
        if (passwordMatches) {
            String token = jwtTokenProvider.generateToken(loginRequest.getEmail());
            System.out.println("Generated Token: " + token);  // Log the generated token
            return ResponseEntity.ok("Bearer " + token);
        } else {
            System.out.println("Invalid credentials for email: " + loginRequest.getEmail());  // Log invalid login attempt
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
