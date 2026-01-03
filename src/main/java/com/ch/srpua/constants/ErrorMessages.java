package com.ch.srpua.constants;

/**
 * Error messages used throughout the application.
 */
public final class ErrorMessages {
    
    private ErrorMessages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // User Errors
    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";

    // Token Errors
    public static final String INVALID_REFRESH_TOKEN = "Invalid refresh token";
    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token expired";
    public static final String INVALID_TOKEN_TYPE = "Invalid token type";
    public static final String TOKEN_EXPIRED = "Token expired";
    public static final String TOKEN_INVALID = "Invalid token";

    // Role Errors
    public static final String ROLE_NOT_FOUND = "Role not found";

    // General Errors
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
    public static final String AUTHENTICATION_FAILED = "Authentication failed";
}

