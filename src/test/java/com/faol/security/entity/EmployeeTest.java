package com.faol.security.entity;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    List<Role> roles;
    Employee employee;
    Employee employeeB;

    @BeforeEach
    void init(){
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
                .build();

    }

    @Test
    void getRoles() {
        //given
        employee.setRoles(roles);
        //when
        List<Role> result = employee.getRoles();
        //then
        assertNotNull(result);
        assertEquals(roles, result);
        assertInstanceOf(java.util.ArrayList.class, result);
    }

    @Test
    void setId_employee() {
        //given
        Long id = 5371L;
        //when
        employee.setId_employee(id);
        //then
        assertNotNull(employee.getId_employee());
        assertEquals(id, employee.getId_employee());
        assertInstanceOf(Long.class, employee.getId_employee());
    }

    @Test
    void setName() {
        //given
        String name = "Domingo";
        //when
        employee.setName(name);
        //then
        assertNotNull(employee.getName());
        assertEquals(name, employee.getName());
        assertInstanceOf(String.class, employee.getName());
    }

    @Test
    void setLastname() {
        //given
        String lastname = "Perez";
        //when
        employee.setLastname(lastname);
        //then
        assertNotNull(employee.getLastname());
        assertEquals(lastname, employee.getLastname());
        assertInstanceOf(String.class, employee.getLastname());
    }

    @Test
    void setEmail() {
        //given
        String email = "domingop123@mail.com";
        //when
        employee.setEmail(email);
        //then
        assertNotNull(employee.getEmail());
        assertEquals(email, employee.getEmail());
        assertInstanceOf(String.class, employee.getEmail());
    }

    @Test
    void setUsername() {
        //given
        String username = "domin";
        //when
        employee.setUsername(username);
        //then
        assertNotNull(employee.getUsername());
        assertEquals(username, employee.getUsername());
        assertInstanceOf(String.class, employee.getUsername());
    }

    @Test
    void EmployeeBuilderTest(){
        //given
        //when
        //then
        assertNotNull(employeeB);
        assertInstanceOf(Employee.class, employeeB);
        assertEquals(roles, employeeB.getRoles());

    }

    @Test
    void toStringTest(){

        String employeeString = "Employee{" +
                "id_employee=" + employeeB.getId_employee() +
                ", name='" + employeeB.getName() + '\'' +
                ", lastname='" + employeeB.getLastname() + '\'' +
                ", email='" + employeeB.getEmail() + '\'' +
                ", username='" + employeeB.getUsername() + '\'' +
                ", password='" + employeeB.getPassword() + '\'' +
                ", roles=" + roles +
                '}';

        assertNotNull(employee);
        assertEquals(employeeB.toString(), employeeString);
        assertInstanceOf(String.class, employeeB.toString());
    }
}