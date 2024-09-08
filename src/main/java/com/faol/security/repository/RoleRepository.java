package com.faol.security.repository;

import com.faol.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Repositorio del Entity Role
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Transactional(readOnly = true)
    Optional<Role> findByName(String name);
}
