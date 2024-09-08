package com.faol.security.repository;

import com.faol.security.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repositorio del Entity Employee
 */
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Transactional(readOnly = true)
    Optional<Employee> findByUsername(String username);
}
