package com.ch.srpua.api.v1.admin;

import com.ch.srpua.domain.User;
import com.ch.srpua.service.admin.AdminService;
import com.ch.srpua.config.SwaggerApisDocsV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for admin operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Admin management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AdminControllerV1 {
    private final AdminService adminService;

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Returns all users (Admin only)", responses = @ApiResponse(responseCode = "200", description = "List of users", content = @Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = SwaggerApisDocsV1.USERS_LIST_RESPONSE))))
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = adminService.getAllUsers();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("total", users.size());
        return ResponseEntity.ok(response);
    }
}

