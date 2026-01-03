package com.ch.srpua.service.auth;

import com.ch.srpua.config.JwtProperties;
import com.ch.srpua.constants.ErrorMessages;
import com.ch.srpua.constants.ValidationConstants;
import com.ch.srpua.domain.Role;
import com.ch.srpua.domain.User;
import com.ch.srpua.exception.UserError;
import com.ch.srpua.respository.RoleRepository;
import com.ch.srpua.respository.UserRepository;
import com.ch.srpua.util.JwtUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for authentication operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final Validator validator;

    @Transactional
    public Map<String, String> register(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new UserError(HttpStatus.CONFLICT.value(), ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new UserError(ErrorMessages.ROLE_NOT_FOUND));

        User user = User.builder()
                .email(email)
                .password(password)
                .roles(Set.of(userRole))
                .build();

        validateEntity(user);

        user.setPassword(passwordEncoder.encode(password));
        user = userRepository.save(user);

        UserDetails userDetails = createUserDetails(user);
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        updateUserRefreshToken(user, refreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("email", user.getEmail());

        return response;
    }

    @Transactional
    public Map<String, String> login(String email, String password) {
        User tempUser = User.builder()
                .email(email)
                .password(password)
                .build();
        validateEntity(tempUser);
        
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            User user = userRepository.findByEmailWithRoles(email)
                    .orElseThrow(() -> new UserError(ErrorMessages.USER_NOT_FOUND));

            UserDetails userDetails = createUserDetails(user);

            String accessToken = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            updateUserRefreshToken(user, refreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("email", user.getEmail());
            return response;
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UserError(ErrorMessages.INVALID_CREDENTIALS);
        }
    }

    @Transactional
    public Map<String, String> refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new UserError(HttpStatus.BAD_REQUEST.value(), ValidationConstants.REFRESH_TOKEN_REQUIRED_MESSAGE);
        }

        String email = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new UserError(ErrorMessages.USER_NOT_FOUND));

        validateRefreshToken(user, refreshToken);

        UserDetails userDetails = createUserDetails(user);
        String newAccessToken = jwtUtil.generateToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        updateUserRefreshToken(user, newRefreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken);
        return response;
    }

    private void validateRefreshToken(User user, String refreshToken) {
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new UserError(ErrorMessages.INVALID_REFRESH_TOKEN);
        }

        if (user.getRefreshTokenExpiry() == null || user.getRefreshTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new UserError(ErrorMessages.REFRESH_TOKEN_EXPIRED);
        }

        if (!jwtUtil.isRefreshToken(refreshToken)) {
            throw new UserError(ErrorMessages.INVALID_TOKEN_TYPE);
        }
    }

    private void updateUserRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(LocalDateTime.now().plusSeconds(
                jwtProperties.getRefreshTokenExpirationMs() / 1000));
        userRepository.save(user);
    }

    private void validateEntity(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new UserError(HttpStatus.BAD_REQUEST.value(), errorMessage);
        }
    }

    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName().name()))
                        .toList())
                .build();
    }
}

