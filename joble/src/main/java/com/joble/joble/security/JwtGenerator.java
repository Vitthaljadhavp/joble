package com.joble.joble.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtGenerator {
    public static void main(String[] args) {
        String secretKey = "ij/wNZAJnW2CD/DmJDiJU3JEHcNKedPbGUIFEMEcpMY=";  // Use the secret key you configured in your application
        String token = Jwts.builder()
                .setSubject("dj")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))  // Expires in 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.out.println("Generated Token: " + token);
    }
}
