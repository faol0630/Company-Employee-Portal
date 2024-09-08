package com.faol.security.service;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.entity.Company;

import java.util.List;
import java.util.Optional;


public interface CompanyServiceInt {

    List<CompanyDTO> getAllCompanies();
    Optional<CompanyDTO> getCompanyById(Integer company_id);
    void newCompany(Company company);
    CompanyDTO updateCompany(Company company, Integer id_company);
    void deleteCompanyById(Integer id_company);
    void deleteAllCompanies();
}
