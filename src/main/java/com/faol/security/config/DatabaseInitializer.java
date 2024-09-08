/*
package com.faol.security.config;

import com.faol.security.entity.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.faol.security.repository.CompanyRepo;
import com.faol.security.repository.EmployeeRepo;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @PostConstruct
    @Transactional
    public void init() {
        List<Role> roles1 = new ArrayList<>();

        // Create and save a company
        Company company = Company.builder()
                .company_id(645)
                .company_name("Autos S.A")
                .country("Colombia")
                .departmentList(null)
                .address(null)
                .build();

        companyRepo.save(company);

        // Create and save a department
        Department department = Department.builder()
                .department_id(74152)
                .company(null)
                .deparment_name("Logica y programacion")
                .employeeList(new ArrayList<>())
                .build();

        // Create and save an address
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setNumber("456");
        address.setZipcode("78910");

        // Create and save an employee
        Employee employee = new Employee();
        employee.setId_employee(948953L);
        employee.setName("Juan");
        employee.setLastname("Ruiz");
        employee.setEmail("juanruiz43@example.com");
        employee.setUsername("juanr");
        employee.setPassword("12345");
        employee.setAddress(address);
        employee.setDepartment(department);
        employee.setRoles(roles1);
        employeeRepo.save(employee);
    }
}

*/
