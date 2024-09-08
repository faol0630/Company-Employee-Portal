package com.faol.security.dto;

import com.faol.security.entity.Address;
import com.faol.security.entity.Company;
import com.faol.security.entity.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDTOTest {

    EmployeeDTO employeeDTO = new EmployeeDTO();

    @Test
    void setId_employee() {
        //given
        Long id = 3546L;
        //when
        employeeDTO.setId_employee(id);
        //then
        assertNotNull(employeeDTO.getId_employee());
        assertEquals(id, employeeDTO.getId_employee());
        assertInstanceOf(Long.class, employeeDTO.getId_employee());
    }

    @Test
    void setName() {
        String name = "Jorge";
        employeeDTO.setName(name);
        Assertions.assertAll( "EmployeeDTO test",
                () -> assertNotNull(employeeDTO.getName()),
                () -> assertEquals(name, employeeDTO.getName()),
                () -> assertInstanceOf(String.class, employeeDTO.getName())
        );
    }

    @Test
    void setLastname() {
        String lastname = "Lopez";
        employeeDTO.setLastname(lastname);
        Assertions.assertAll( "EmployeeDTO test",
                () -> assertNotNull(employeeDTO.getLastname()),
                () -> assertEquals(lastname, employeeDTO.getLastname()),
                () -> assertInstanceOf(String.class, employeeDTO.getLastname())
        );
    }

    @Test
    void setEmail() {
        String email = "jorgelop32@mail.net";
        employeeDTO.setEmail(email);
        Assertions.assertAll( "EmployeeDTO test",
                () -> assertNotNull(employeeDTO.getEmail()),
                () -> assertEquals(email, employeeDTO.getEmail()),
                () -> assertInstanceOf(String.class, employeeDTO.getEmail())
        );
    }

    @Test
    void setUsername() {
        String username = "jorge";
        employeeDTO.setUsername(username);
        Assertions.assertAll( "EmployeeDTO test",
                () -> assertNotNull(employeeDTO.getUsername()),
                () -> assertEquals(username, employeeDTO.getUsername()),
                () -> assertInstanceOf(String.class, employeeDTO.getUsername())
        );
    }

    @Test
    void setAddress() {
        Address address = Address.builder()
                .address_id(4531)
                .street("mozzarella")
                .number("5")
                .zipcode("46571")
                .build();

        employeeDTO.setAddress(address);
        Assertions.assertAll( "EmployeeDTO test",
                () -> assertNotNull(employeeDTO.getAddress()),
                () -> assertEquals(address, employeeDTO.getAddress()),
                () -> assertInstanceOf(Address.class, employeeDTO.getAddress())
        );
    }

    @Test
    void setDepartment() {
        Department department = Department.builder()
                .department_id(765)
                .company(new Company())
                .deparment_name("Ayuda general")
                .employeeList(new ArrayList<>())
                .build();

        employeeDTO.setDepartment(department);
        Assertions.assertAll( "EmployeeDTO test",
                () -> assertNotNull(employeeDTO.getDepartment()),
                () -> assertEquals(department, employeeDTO.getDepartment()),
                () -> assertInstanceOf(Department.class, employeeDTO.getDepartment())
        );
    }

    @Test
    void hashCodeConsistencyTest(){

        Address address = new Address(4531, "Main St", "2","12345");
        Department department = new Department(1, "HR", null, null);

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id_employee(44332L)
                .name("Ana")
                .lastname("Saa")
                .email("anasaa21@net.com")
                .username("anasa")
                .department(department)
                .address(address)
                .build();

        int initialHashCode = employeeDTO.hashCode();
        int subSequentHashCode = employeeDTO.hashCode();
        System.out.println(initialHashCode);
        System.out.println(subSequentHashCode);

        assertEquals(initialHashCode, subSequentHashCode, "Hash code should be consistent");

    }

    @Test
    void hashCodeEqualityTest(){

        Address address = new Address(4531, "Main St", "2","12345");
        Department department = new Department(1, "HR", null, null);

        EmployeeDTO employeeDTO1 = EmployeeDTO.builder()
                .id_employee(44332L)
                .name("Ana")
                .lastname("Saa")
                .email("anasaa21@net.com")
                .username("anasa")
                .department(department)
                .address(address)
                .build();

        EmployeeDTO employeeDTO2 = EmployeeDTO.builder()
                .id_employee(44332L)
                .name("Ana")
                .lastname("Saa")
                .email("anasaa21@net.com")
                .username("anasa")
                .department(department)
                .address(address)
                .build();

        int initialHashCode = employeeDTO1.hashCode();
        int subSequentHashCode = employeeDTO2.hashCode();
        System.out.println(initialHashCode);
        System.out.println(subSequentHashCode);

        assertEquals(employeeDTO1, employeeDTO2, "Hash codes should be equal for equal objects");

    }

    @Test
    void equalsSameObjectTest(){

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id_employee(44332L)
                .name("Ana")
                .lastname("Saa")
                .email("anasaa21@net.com")
                .username("anasa")
                .department(new Department())
                .address(new Address())
                .build();


        assertTrue(employeeDTO.equals(employeeDTO));

    }

    /*@Test
    void equalsTwoObjectTest(){

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id_employee(44332L)
                .name("Ana")
                .lastname("Saa")
                .email("anasaa21@net.com")
                .username("anasa")
                .department(new Department())
                .address(new Address())
                .build();

        EmployeeDTO employeeDTO1 = EmployeeDTO.builder()
                .id_employee(44332L)
                .name("Ana")
                .lastname("Saa")
                .email("anasaa21@net.com")
                .username("anasa")
                .department(new Department())
                .address(new Address())
                .build();


        Assertions.assertAll(
                *//*() -> assertEquals(employeeDTO.getId_employee(), employeeDTO1.getId_employee()),
                () -> assertEquals(employeeDTO.getName(), employeeDTO1.getName()),
                () -> assertEquals(employeeDTO.getLastname(), employeeDTO1.getLastname()),
                () -> assertEquals(employeeDTO.getUsername(), employeeDTO1.getUsername()),
                () -> assertEquals(employeeDTO.getEmail(), employeeDTO1.getEmail()),*//*
                *//*() -> assertFalse(Objects.equals(employeeDTO.getId_employee(), null)),
                () -> assertFalse(Objects.equals(employeeDTO.getName(), null)),
                () -> assertFalse(Objects.equals(employeeDTO.getLastname(), null)),
                () -> assertFalse(Objects.equals(employeeDTO.getEmail(), null)),
                () -> assertFalse(Objects.equals(employeeDTO.getUsername(), null))*//*
        );

    }*/

    @Test
    void employeeDTOBuilderToStringTest(){

        EmployeeDTO.EmployeeDTOBuilder builder = EmployeeDTO.builder()
                .id_employee(4321L)
                .name("Ana")
                .lastname("Lopez")
                .email("analop22@net.es")
                .username("analo")
                .address(new Address())
                .department(new Department());

        String builderToString = builder.toString();

        Assertions.assertAll(
                () -> assertDoesNotThrow( () -> builder.username("analo")),
                () -> assertInstanceOf(EmployeeDTO.EmployeeDTOBuilder.class, builder),
                () -> assertNotNull(builderToString)
        );
    }

}