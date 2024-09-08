/*
package com.faol.security.repository;

import com.faol.security.entity.Address;
import com.faol.security.entity.Department;
import com.faol.security.entity.Employee;
import com.faol.security.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


//1)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//para usar mysql
@ActiveProfiles("test")
@DataJpaTest
class EmployeeRepoTest {

    Long valid_employee_id = 534L;
    Long invalid_employee_id = 123534L;

    @Mock //2)
    EmployeeRepo employeeRepo;

    @BeforeEach
    void setUp() {

        Department department = new Department();

        Address address = new Address();

        List<Role> roles = new ArrayList<>();

        Employee employee = Employee.builder()
                .id_employee(valid_employee_id)
                .name("Juan")
                .lastname("Garcia")
                .email("juang24@mail.net")
                .username("juang")
                .password("12345")
                .department(department)
                .address(address)
                .roles(roles)
                .build();

        //employeeRepo.save(employee);

    }

    @Test
    void findByUsername() {
    }

    @Test
    void findByIdEmpty(){
        var result = this.employeeRepo.findById(valid_employee_id);
        System.out.println("result: " + result);
        assertThat(result).isEmpty(); //si lo pasa pero deberia ser isPresent()
    }
}*/
