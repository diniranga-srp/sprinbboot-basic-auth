package com.ch.srpua.config;


import com.ch.srpua.domain.Role;
import com.ch.srpua.respository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Data initializer that runs on application startup.
 * Ensures that required roles exist in the database.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String @NonNull ... args) {
        log.info("Initializing default roles...");
        
        if (roleRepository.findByName(Role.RoleName.ROLE_USER).isEmpty()) {
            roleRepository.save(Role.builder().name(Role.RoleName.ROLE_USER).build());
            log.info("Created ROLE_USER");
        }
        
        if (roleRepository.findByName(Role.RoleName.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(Role.builder().name(Role.RoleName.ROLE_ADMIN).build());
            log.info("Created ROLE_ADMIN");
        }
        
        log.info("Role initialization completed");
    }
}
