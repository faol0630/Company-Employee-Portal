package com.faol.security.service;

import com.faol.security.dto.EmployeeDTO;
import com.faol.security.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceInt {

    List<EmployeeDTO> getAllEmployees();
    Optional<EmployeeDTO> getEmployeeById(Long id_employee);
    void newEmployee(Employee employee);
    EmployeeDTO updateEmployee(Employee employee, Long id_employee);
    void deleteEmployeeById(Long id_employee);
    void deleteAllEmployees();
}
