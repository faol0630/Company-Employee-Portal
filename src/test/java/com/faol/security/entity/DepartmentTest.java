package com.faol.security.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department = new Department();

    @Test
    void setDepartment_id() {
        int id = 4993;
        department.setDepartment_id(id);
        Assertions.assertAll( "Department setters test",
                () -> assertInstanceOf(Integer.class , department.getDepartment_id()),
                () -> assertNotNull(department.getDepartment_id()),
                () -> assertEquals(id, department.getDepartment_id()),
                () -> assertDoesNotThrow( () -> department.setDepartment_id(id))
        );
    }

    @Test
    void setDeparment_name() {
        String name = "Domicilios";
        department.setDeparment_name(name);
        Assertions.assertAll( "Department setters test",
                () -> assertInstanceOf(String.class , department.getDeparment_name()),
                () -> assertNotNull(department.getDeparment_name()),
                () -> assertEquals(name, department.getDeparment_name()),
                () -> assertDoesNotThrow( () -> department.setDeparment_name(name))
        );
    }

    @Test
    void setEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        department.setEmployeeList(employeeList);
        Assertions.assertAll( "Department setters test",
                () -> assertInstanceOf(java.util.ArrayList.class, department.getEmployeeList()),
                () -> assertEquals(employeeList, department.getEmployeeList()),
                () -> assertNotNull(department.getEmployeeList()),
                () -> assertDoesNotThrow( () -> department.setEmployeeList(employeeList))


        );
    }

    @Test
    void setCompany() {
        Company company = new Company();
        department.setCompany(company);
        Assertions.assertAll( "Department setters test",
                () -> assertInstanceOf(Company.class , department.getCompany()),
                () -> assertNotNull(department.getCompany()),
                () -> assertEquals(company, department.getCompany()),
                () -> assertDoesNotThrow( () -> department.setCompany(company))
        );
    }

    @Test
    void departmentToStringTest(){
        Department department1 = Department.builder()
                .department_id(57575)
                .deparment_name("Mantenimiento")
                .company(new Company())
                .employeeList(new ArrayList<>())
                .build();

        String result = department1.toString();
        String expected = "Department{" +
                "department_id=" + department1.getDepartment_id() +
                ", deparment_name='" + department1.getDeparment_name() + '\'' +
                //", employeeList=" + department1.getEmployeeList() +
                //", company=" + department1.getCompany() +
                '}';

        assertEquals(expected, result);
    }

    @Test
    void departmentBuilderToStringTest(){

        Company company = new Company(342, "TLC", "Spain", new ArrayList<>(), new Address());
        List<Employee> employeeList = new ArrayList<>();

        Department.DepartmentBuilder builder = Department.builder()
                .department_id(2314)
                .company(company)
                .deparment_name("Finanzas")
                .employeeList(employeeList);

        String builderToString = builder.toString();

        Assertions.assertAll(
                () -> assertNotNull(builderToString),
                () -> assertInstanceOf(Department.DepartmentBuilder.class, builder),
                () -> assertDoesNotThrow(builder::toString)

        );
    }

}