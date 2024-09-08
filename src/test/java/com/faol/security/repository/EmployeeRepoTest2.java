/*
package com.faol.security.repository;

import com.faol.security.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepoTest2 {

    Employee employeeTest;
    Company company;
    Department department;
    Address address;
    Role role;

    @Autowired //1)
    EmployeeRepo employeeRepo;

    @Autowired //2)
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {

        List<Employee> employeeList = new ArrayList<>();

        address = Address.builder()
                .number("2")
                .street("Rua DE")
                .zipcode("45345")
                .build();
        testEntityManager.persist(address);
        testEntityManager.flush();

        company = Company.builder()
                .country("Spain")
                .company_name("Suministros.S.A")
                .address(address)
                .departmentList(new ArrayList<>())
                .build();
        testEntityManager.persist(company);
        testEntityManager.flush();

        department = Department.builder()
                .deparment_name("Suministros")
                .company(company)
                .employeeList(employeeList)
                .build();

        testEntityManager.persist(department);
        testEntityManager.flush();

        role = new Role();
        role.setName("USER");
        List<Role> roles = new ArrayList<>();
        testEntityManager.persist(role);
        testEntityManager.flush();

        employeeTest = Employee.builder()
                .id_employee(615233L)
                .name("Juan")
                .lastname("Garcia")
                .email("juang24@mail.net")
                .username("juang")
                .password("12345")
                .department(department)
                .address(address)
                .roles(roles)
                .build();

        testEntityManager.persist(employeeTest);
        testEntityManager.flush();
    }

    @Test
    void findByUsername() {
        Optional<Employee> employeeOptional = employeeRepo.findByUsername("juang");
        assertEquals(employeeOptional.get().getUsername(), "juang");
        assertTrue(employeeOptional.isPresent());
    }
}*/
