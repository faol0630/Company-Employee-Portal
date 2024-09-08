package com.faol.security.repository;

import com.faol.security.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio del Entity Department
 */

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
}
