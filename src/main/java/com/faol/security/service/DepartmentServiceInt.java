package com.faol.security.service;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.entity.Company;
import com.faol.security.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentServiceInt {

    List<Department> getAllDepartments();
    Optional<Department> getDepartmentById(Integer department_id);
    void newDepartment(Department department);
    Department updateDepartment(Department department, Integer department_id);
    void deleteDepartmentById(Integer department_id);
    void deleteAllDepartments();
}
