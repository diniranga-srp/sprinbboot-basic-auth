package com.ch.srpua.service.user;

import com.ch.srpua.constants.ErrorMessages;
import com.ch.srpua.domain.User;
import com.ch.srpua.exception.UserError;
import com.ch.srpua.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for user operations.
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

