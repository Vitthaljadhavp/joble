package com.joble.joble.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;


@Service
public class JwtTokenProvider {

    private static final String SECRET_KEY = "your-secret-key";  // Replace with a stronger secret key

    // Generate JWT token
    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(key) // Using updated signWith method with Key
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder() // Updated method to parse JWT
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Updated method to set signing key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Get username from JWT token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder() // Updated method to parse JWT
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Updated method to set signing key
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
