package com.joble.joble.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.joble.joble.security.JwtTokenProvider;

@RestController
public class TokenValidateController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Invalid token format");
        }

        String token = authorizationHeader.substring(7); // Extract token from "Bearer <token>"
        boolean isValid = jwtTokenProvider.validateToken(token);

        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}
