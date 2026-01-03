package com.ch.srpua.constants;

/**
 * Constants for validation rules and error messages.
 */
public final class ValidationConstants {

    private ValidationConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Password
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 128;
    public static final String PASSWORD_REQUIRED_MESSAGE = "Password is required";
    public static final String PASSWORD_MIN_LENGTH_MESSAGE = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";

    // Email
    public static final int MAX_EMAIL_LENGTH = 255;
    public static final String EMAIL_REQUIRED_MESSAGE = "Email is required";
    public static final String EMAIL_INVALID_MESSAGE = "Email should be valid";

    // Token
    public static final String REFRESH_TOKEN_REQUIRED_MESSAGE = "Refresh token is required";
    public static final String ACCESS_TOKEN_REQUIRED_MESSAGE = "Access token is required";
    public static final String TOKEN_INVALID_FORMAT_MESSAGE = "Token format is invalid";
}
