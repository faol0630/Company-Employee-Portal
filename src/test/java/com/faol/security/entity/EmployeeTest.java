package com.faol.security.entity;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    List<Role> roles;
    Employee employee;
    Employee employeeB;
    Department department;
    Address address;

    @BeforeEach
    void init() {

        address = Address.builder()
                .address_id(53664)
                .number("4")
                .street("Balcones")
                .zipcode("64758")
                .build();

        department = Department.builder()
                .department_id(35224)
                .deparment_name("Varios")
                .company(new Company())
                .employeeList(new ArrayList<>())
                .build();

        roles = new ArrayList<>();

        Role role1 = new Role();
        role1.setId_role(38472L);
        role1.setName("user");

        Role role2 = new Role();
        role2.setId_role(417L);
        role2.setName("admin");

        roles.add(role1);
        roles.add(role2);

        employee = new Employee();

        employeeB = Employee.builder()
                .id_employee(45092L)
                .name("Alicia")
                .lastname("Saa")
                .username("alice")
                .password("12345")
                .email("aliciasaa3421@net.com")
                .roles(roles)
                .department(department)
                .address(address)
                .build();

    }

    @Test
    void getRoles() {
        //given
        employee.setRoles(roles);
        //when
        List<Role> result = employee.getRoles();
        //then

        Assertions.assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(roles, result),
                () -> assertInstanceOf(java.util.ArrayList.class, result),
                () -> assertDoesNotThrow(() -> employee.setRoles(roles))
        );

    }

    @Test
    void setId_employee() {
        //given
        Long id = 5371L;
        //when
        employee.setId_employee(id);
        //then

        Assertions.assertAll(
                () -> assertNotNull(employee.getId_employee()),
                () -> assertEquals(id, employee.getId_employee()),
                () -> assertInstanceOf(Long.class, employee.getId_employee()),
                () -> assertDoesNotThrow(() -> employee.setId_employee(id))
        );

    }

    @Test
    void setName() {
        //given
        String name = "Domingo";
        //when
        employee.setName(name);
        //then
        Assertions.assertAll(
                () -> assertNotNull(employee.getName()),
                () -> assertEquals(name, employee.getName()),
                () -> assertInstanceOf(String.class, employee.getName()),
                () -> assertDoesNotThrow(() -> employee.setName(name))
        );

    }

    @Test
    void setLastname() {
        //given
        String lastname = "Perez";
        //when
        employee.setLastname(lastname);
        //then

        Assertions.assertAll(
                () -> assertNotNull(employee.getLastname()),
                () -> assertEquals(lastname, employee.getLastname()),
                () -> assertInstanceOf(String.class, employee.getLastname()),
                () -> assertDoesNotThrow(() -> employee.setLastname(lastname))
        );

    }

    @Test
    void setEmail() {
        //given
        String email = "domingop123@mail.com";
        //when
        employee.setEmail(email);
        //then

        Assertions.assertAll(
                () -> assertNotNull(employee.getEmail()),
                () -> assertEquals(email, employee.getEmail()),
                () -> assertInstanceOf(String.class, employee.getEmail()),
                () -> assertDoesNotThrow(() -> employee.setEmail(email))
        );

    }

    @Test
    void setUsername() {
        //given
        String username = "domin";
        //when
        employee.setUsername(username);
        //then

        Assertions.assertAll(
                () -> assertNotNull(employee.getUsername()),
                () -> assertEquals(username, employee.getUsername()),
                () -> assertInstanceOf(String.class, employee.getUsername()),
                () -> assertDoesNotThrow(() -> employee.setUsername(username))
        );

    }

    @Test
    void setAddress() {
        //given (dentro del metodo init)

        //when
        employee.setAddress(address);
        //then

        Assertions.assertAll(
                () -> assertNotNull(employee.getAddress()),
                () -> assertEquals(address, employee.getAddress()),
                () -> assertInstanceOf(Address.class, employee.getAddress()),
                () -> assertDoesNotThrow(() -> employee.setAddress(address))
        );


    }

    @Test
    void setDepartment() {
        //given(dentro del metodo init)

        //when
        employee.setDepartment(department);
        //then
        Assertions.assertAll(
                () -> assertNotNull(employee.getDepartment()),
                () -> assertEquals(department, employee.getDepartment()),
                () -> assertInstanceOf(Department.class, employee.getDepartment()),
                () -> assertDoesNotThrow(() -> employee.setDepartment(department))
        );


    }

    @Test
    void employeeBuilderTest() {
        //given
        //when
        //then

        Assertions.assertAll(
                () -> assertNotNull(employeeB),
                () -> assertInstanceOf(Employee.class, employeeB),
                () -> assertEquals(roles, employeeB.getRoles())
        );


    }

    @Test
    void toStringTest() {
        //given(dentro del metodo init)
        String result = employeeB.toString();
        String expected = "Employee{" +
                "id_employee=" + employeeB.getId_employee() +
                ", name='" + employeeB.getName() + '\'' +
                ", lastname='" + employeeB.getLastname() + '\'' +
                ", email='" + employeeB.getEmail() + '\'' +
                ", username='" + employeeB.getUsername() + '\'' +
                ", password='" + employeeB.getPassword() + '\'' +
                //", department=" + department +
                ", roles=" + roles +
                ", address=" + address +
                '}';

        Assertions.assertAll(
                () -> assertNotNull(employeeB),
                () -> assertEquals(expected, result),
                () -> assertInstanceOf(String.class, employeeB.toString())
        );

    }

    @Test
    void employeeBuilderToStringTest(){

        Employee.EmployeeBuilder builder = Employee.builder()
                .id_employee(342511L)
                .name("Pedro")
                .lastname("Saa")
                .email("pedrito12@mail.net")
                .username("pedro")
                .password("12345")
                .address(new Address())
                .department(new Department())
                .roles(new ArrayList<>());

        String builderToString = builder.toString();

        Assertions.assertAll(
                () -> assertDoesNotThrow( () -> builder.lastname("Saa")),
                () -> assertInstanceOf( Employee.EmployeeBuilder.class, builder),
                () -> assertNotNull(builderToString)
        );

    }

}