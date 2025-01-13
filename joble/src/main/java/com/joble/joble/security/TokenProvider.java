package com.joble.joble.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    // Hardcode a valid JWT token for testing
    private final String hardcodedToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaiIsImlhdCI6MTczNjc3Mzg2NCwiZXhwIjoxNzM2Nzc3NDY0fQ.GAkoZ5o9TOa2GZIdMC5P8RXKD-XCocjRaG1xgJCy14s";  // Replace with a real token for testing

    public String getBearerToken() {
        return hardcodedToken;
    }

}
