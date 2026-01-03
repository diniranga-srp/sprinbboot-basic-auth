package com.ch.srpua.respository;

import com.ch.srpua.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity.
 * Provides data access operations for roles.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /** Finds a role by its name. */
    Optional<Role> findByName(Role.RoleName name);
}
