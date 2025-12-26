package com.example.demo.service;

import com.example.demo.constant.ErrorMessages;
import com.example.demo.entity.User;
import com.example.demo.exception.UserError;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for user operations.
 * Handles user-related operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getCurrentUser(String email) {
        return userRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new UserError(ErrorMessages.USER_NOT_FOUND));
    }
}
