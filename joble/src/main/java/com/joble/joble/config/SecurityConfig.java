package com.joble.joble.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity during development
            .csrf(csrf -> csrf.disable())
            // Configure authorization rules
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Allow unauthenticated access to registration
                .anyRequest().authenticated() // Protect all other endpoints
            )
            // Disable HTTP Basic and Form Login (optional, depending on your requirements)
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable());

        return http.build();
    }
}
