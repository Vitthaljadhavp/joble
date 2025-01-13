package com.joble.joble.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final SecretKey secretKey;
    private final long expiration;
    private final TokenProvider tokenProvider;  // Inject TokenProvider

    // Constructor to initialize the secret key, expiration, and TokenProvider
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secret,
                            @Value("${jwt.expiration}") long expiration,
                            TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;  // Set the TokenProvider
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));  // Use the correct key property
        this.expiration = expiration;
    }

    // Generate JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Token valid for the configured duration
                .signWith(secretKey)
                .compact();
    }

    // Validate JWT token with enhanced error logging
    public boolean validateToken(String token) {
        log.debug("Using token for validation: {}", token);  // Added logging here to debug the token
        
        try {
            // Claims claims = Jwts.parserBuilder()
            //         .setSigningKey(secretKey)
            //         .build()
            //         .parseClaimsJws(token)
            //         .getBody();

            // Token is valid if it has not expired
            // if (claims.getExpiration().before(new Date())) {
            //     log.error("JWT token has expired");
            //     return false;
            // }
            // return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            //log.error("JWT token has expired: {}", e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            //log.error("Invalid JWT token: {}", e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            //log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            //log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (Exception e) {
            //log.error("Error validating JWT token: {}", e.getMessage());
        }
        return false;
    }

    // Extract username from JWT token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Retrieve the bearer token (via TokenProvider)
    public String getBearerToken() {
        return tokenProvider.getBearerToken();
    }
}
