package com.faol.security.repository;

import com.faol.security.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositorio del Entity Company
 */
@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {
}
