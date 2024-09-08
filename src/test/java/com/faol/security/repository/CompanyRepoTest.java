package com.faol.security.repository;

import com.faol.security.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ActiveProfiles("test")
//@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //para no usar H2 DB
class CompanyRepoTest {

    Company company;
    Address addressCompany;
    Address addressEmployee;
    Department department1;
    List<Department> departmentList;
    List<Employee> employeeList;
    List<Role> roles;
    Employee employee;
    Role role;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {

        roles = new ArrayList<>();

        role = Role.builder()
                .id_role(1L)
                .name("USER")
                .build();

        roles.add(role);

        addressCompany = Address.builder()
                .number("2")
                .street("Rua DE")
                .zipcode("45345")
                .build();

        addressEmployee = Address.builder()
                .number("3")
                .street("Street One")
                .zipcode("41325")
                .build();

        departmentList = new ArrayList<>();

        employeeList = new ArrayList<>();

        employee = Employee.builder()
                .id_employee(615233L)
                .name("Juan")
                .lastname("Garcia")
                .email("juang24@mail.net")
                .username("juang")
                .password("12345")
                .department(department1)
                .address(addressEmployee)
                .roles(roles)
                .build();

        employeeList.add(employee);

        department1 = Department.builder()
                .department_id(34521234)
                .deparment_name("Recursos Humanos")
                .company(company)
                .employeeList(employeeList)
                .build();

        departmentList.add(department1);

        company = Company.builder()
                .company_id(546)
                .country("Spain")
                .company_name("Suministros.S.A")
                .address(addressCompany)
                .departmentList(new ArrayList<>())
                .build();
        //para que funcione findByIdTest:
        companyRepo.save(company);

    }

    //@Rollback(false)
    /*@Test
    void findAllTest(){
        List<Company> companyList = companyRepo.findAll();

        Assertions.assertAll(
                () -> assertDoesNotThrow( () -> companyRepo.findAll()),
                () -> assertThat(companyList).isNotNull(),
                () -> assertThat(companyList).isNotEmpty()
        );
    }*/

    @Test
    void findByIdTest(){ //company alojada en mysql db

        Optional<Company> founded = companyRepo.findById(29);

        System.out.println("founded: " + founded);

        Assertions.assertAll( "Company repository test: ",
                () -> assertThat(founded).isNotNull(),
                () -> assertThat(founded).isPresent(),
                () -> assertEquals(founded.get().getCountry(), "italy"),
                () -> assertDoesNotThrow( () -> companyRepo.findById(29))

        );

    }

    @Test
    void saveCompanyTest(){

        Company savedCompany = companyRepo.save(company);

        Assertions.assertAll( "Company repository test: ",
                () -> assertThat(savedCompany).isNotNull(),
                () -> assertThat(savedCompany.getCountry()).isNotNull(),
                () -> assertThat(savedCompany.getCompany_name()).isEqualTo("Suministros.S.A"),
                () -> assertDoesNotThrow( () -> companyRepo.save(company))

        );
    }



    @Test
    void deleteByIdTest(){

        companyRepo.deleteById(546);

        Assertions.assertAll( "Company repository test: ",
                () -> assertDoesNotThrow( () -> companyRepo.deleteById(546))
        );
    }

    /*@Test
    void deleteAllTest(){

        *//*entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        try{*//*
            companyRepo.deleteAll();

            Assertions.assertAll( "Company repository test: ",
                    () -> assertDoesNotThrow( () -> companyRepo.deleteAll())
            );
        *//*}finally {
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        }*//*


    }*/


}
