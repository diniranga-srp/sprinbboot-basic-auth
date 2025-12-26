package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for JWT token settings.
 * Properties are loaded from application.yaml with prefix "jwt".
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    /** Secret key used for signing JWT tokens (min 32 characters for HS256). */
    private String secret;

    /** Access token expiration time in milliseconds (default: 1 hour). */
    private Long accessTokenExpirationMs;

    /** Refresh token expiration time in milliseconds (default: 24 hours). */
    private Long refreshTokenExpirationMs;
}
