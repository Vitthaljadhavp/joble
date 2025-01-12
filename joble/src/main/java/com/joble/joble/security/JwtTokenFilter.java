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

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log the incoming request URL
        System.out.println("Request URL: " + request.getRequestURL());

        // Retrieve the Authorization header
        String token = request.getHeader("Authorization");

        // Log the token being extracted
        System.out.println("Authorization header: " + token);

        // Check if the token starts with "Bearer "
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " prefix
            System.out.println("Extracted Token: " + token);  // Log the extracted token

            // Validate the token
            if (jwtTokenProvider.validateToken(token)) {
                // Extract username from the token
                String username = jwtTokenProvider.getUsernameFromToken(token);
                System.out.println("Valid Token for User: " + username); // Log the username from token

                // Create an authentication token and set it in the SecurityContext
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, null);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Authentication set for: " + username);  // Log authentication set
            } else {
                System.out.println("Invalid token received: " + token);  // Log invalid token
            }
        } else {
            System.out.println("No token found in request");  // Log when no token is found
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
