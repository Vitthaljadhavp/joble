package com.joble.joble.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component  // Register the filter as a Spring component
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;  // Inject JwtTokenProvider

    private final TokenProvider tokenProvider;

    // public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    //     this.jwtTokenProvider = jwtTokenProvider;
    // }
    public JwtTokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log the incoming request URL
        System.out.println("Request URL: " + request.getRequestURL());

        // Get the hardcoded token from TokenProvider
        String token = tokenProvider.getBearerToken();
        System.out.println("Using hardcoded token: " + token);  // Log the hardcoded token

        // Validate the token (use your own validation logic here)
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " prefix
            System.out.println("Extracted Token: " + token);

            // Add validation logic here (use your existing JWT validation logic)
            boolean isValid = validateToken(token);  // Call your validation method

            if (isValid) {
                // Authentication logic
                String username = "dj";  // Simulate username extraction
                System.out.println("Valid token for: " + username);
                UsernamePasswordAuthenticationToken authenticationToken = 
                    new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("Invalid token received.");
            }
        } else {
            System.out.println("No token found in request.");
        }

        filterChain.doFilter(request, response);
    }

    // Simulate token validation (you can replace with actual validation logic)
    private boolean validateToken(String token) {
        // Just check if the token is not empty for testing
        return token != null && !token.isEmpty();
    }
}
