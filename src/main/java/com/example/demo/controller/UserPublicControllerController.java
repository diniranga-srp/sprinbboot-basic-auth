package com.example.demo.controller;

import com.example.demo.controller.SwaggerExamples;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for authentication operations.
 * Handles user registration, login, and token refresh.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication", description = "Authentication management APIs")
public class UserPublicControllerController {
    private final AuthenticationService authenticationService;

    /** Registers a new user and returns authentication tokens. */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns access and refresh tokens", responses = @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = SwaggerExamples.AUTH_RESPONSE))))
    @RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(name = "Register Request", value = SwaggerExamples.REGISTER_REQUEST)))
    public ResponseEntity<Map<String, String>> register(
            @Valid @org.springframework.web.bind.annotation.RequestBody Map<String, String> request) {
        Map<String, String> response = authenticationService.register(
                request.get("email"), request.get("password"));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /** Authenticates a user and returns tokens. */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates user and returns access and refresh tokens", responses = @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = SwaggerExamples.AUTH_RESPONSE))))
    @RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(name = "Login Request", value = SwaggerExamples.LOGIN_REQUEST)))
    public ResponseEntity<Map<String, String>> login(
            @Valid @org.springframework.web.bind.annotation.RequestBody Map<String, String> request) {
        Map<String, String> response = authenticationService.login(
                request.get("email"), request.get("password"));
        return ResponseEntity.ok(response);
    }
}
