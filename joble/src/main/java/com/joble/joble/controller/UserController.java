package com.joble.joble.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.joble.joble.model.User;
import com.joble.joble.security.JwtTokenProvider;
import com.joble.joble.security.SecurityConfig;
import com.joble.joble.controller.RegisterRequest;
import com.joble.joble.dto.LoginRequest;
import com.joble.joble.service.UserService;

import lombok.Data;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
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
    // Encrypt the password before saving
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));  
    user.setRole(registerRequest.getRole());

    // Save the user using UserService
    userService.registerUser(user);

    return ResponseEntity.status(HttpStatus.CREATED)
                         .body("User registered successfully!");
}


    @PostMapping("/login")
public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
    Optional<User> userOptional = userService.findUserByEmail(loginRequest.getEmail());
    
    if (userOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    User user = userOptional.get();
    // Check if password matches (using BCryptPasswordEncoder)
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // Generate JWT token if credentials are valid
    String token = jwtTokenProvider.generateToken(user.getEmail());
    return ResponseEntity.ok("Bearer " + token);
}


}

// DTO for Registration Request
@Data  // Lombok annotation for generating getters, setters, equals, hashcode, and toString methods automatically
class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;
}
