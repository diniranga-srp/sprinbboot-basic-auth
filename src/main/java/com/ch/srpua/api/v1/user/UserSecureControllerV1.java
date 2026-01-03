package com.ch.srpua.api.v1.user;

import com.ch.srpua.config.SwaggerApisDocsV1;
import com.ch.srpua.domain.User;
import com.ch.srpua.service.auth.AuthenticationService;
import com.ch.srpua.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for user operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User", description = "User management APIs")
@SecurityRequirement(name = "bearerAuth")
public class UserSecureControllerV1 {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns the currently authenticated user's information", responses = @ApiResponse(responseCode = "200", description = "User information", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))))
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Generates new access and refresh tokens using a valid refresh token", responses = @ApiResponse(responseCode = "200", description = "Tokens refreshed", content = @Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = SwaggerApisDocsV1.TOKEN_REFRESH_RESPONSE))))
    @RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(name = "Refresh Token Request", value = SwaggerApisDocsV1.REFRESH_TOKEN_REQUEST)))
    public ResponseEntity<Map<String, String>> refreshToken(
            @Valid @org.springframework.web.bind.annotation.RequestBody Map<String, String> request) {
        Map<String, String> response = authenticationService.refreshToken(request.get("refreshToken"));
        return ResponseEntity.ok(response);
    }
}

