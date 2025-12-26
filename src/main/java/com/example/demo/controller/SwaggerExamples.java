package com.example.demo.controller;

/**
 * Swagger example models for API documentation.
 */
public final class SwaggerExamples {
    
    private SwaggerExamples() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Request Examples
    public static final String REGISTER_REQUEST = "{\"email\":\"user@example.com\",\"password\":\"password123\"}";
    public static final String LOGIN_REQUEST = "{\"email\":\"user@example.com\",\"password\":\"password123\"}";
    public static final String REFRESH_TOKEN_REQUEST = "{\"refreshToken\":\"your-refresh-token-here\"}";

    // Response Examples
    public static final String AUTH_RESPONSE = "{\"accessToken\":\"token\",\"refreshToken\":\"refresh\",\"email\":\"user@example.com\"}";
    public static final String TOKEN_REFRESH_RESPONSE = "{\"accessToken\":\"token\",\"refreshToken\":\"refresh\"}";
    public static final String USERS_LIST_RESPONSE = "{\"users\":[{\"id\":1,\"email\":\"user@example.com\"}],\"total\":1}";
}
