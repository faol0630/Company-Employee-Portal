package com.faol.security.service;

import com.faol.security.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceInt {

    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id_employee);
    void newEmployee(Employee employee);
    Employee updateEmployee(Employee employee, Long id_employee);
    void deleteEmployeeById(Long id_employee);
    void deleteAllEmployees();
}
