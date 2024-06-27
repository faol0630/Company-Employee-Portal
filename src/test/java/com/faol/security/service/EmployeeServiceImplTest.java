package com.faol.security.service;

import com.faol.security.dto.EmployeeDTO;
import com.faol.security.dto.mapper.EmployeeDTOMapper;
import com.faol.security.entity.Employee;
import com.faol.security.entity.Role;
import com.faol.security.exceptions.FieldValidationException;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.repository.EmployeeRepo;
import com.faol.security.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    //1)
    @Mock
    EmployeeRepo employeeRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository;

    String passwordBCrypt;
    EmployeeDTO employeeDTO2;
    Employee employee3;

    //2)
    @InjectMocks
    EmployeeServiceImpl employeeService;

    //3)
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        passwordBCrypt = passwordEncoder.encode("12345");

        employeeDTO2 = EmployeeDTO.builder()
                .id_employee(634L)
                .username("raul")
                .name("raul")
                .lastname("rey")
                .email("raulrey534@com.es")
                .build();

        employee3 = Employee.builder()
                .id_employee(634L)
                .username("raul")
                .name("raul")
                .lastname("rey")
                .email("raulrey534@com.es")
                .password(passwordBCrypt)
                .roles(List.of(new Role(2L, "ROLE_USER")))
                .build();


    }

    @Test
    void getAllEmployees() {
        //5)
        when(employeeRepo.findAll()).thenReturn(buildEmployeeList());//mock
        var result = employeeService.getAllEmployees();

        Assertions.assertAll("Employees test",
                () -> assertFalse(result.isEmpty(), "checking size"),
                () -> assertNotNull(result, "checking nulls"),
                () -> assertNotEquals(result, buildEmployeeList()),
                () -> assertEquals(result, buildEmployeeDTOList())

        );
    }

    @Test
    void getAllEmployeesEmptyList() {
        when(employeeRepo.findAll()).thenReturn(List.of());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.getAllEmployees());

        String message = "Empty list";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(message));

    }

    @Test
    void getEmployeeById() {
        when(employeeRepo.findById(634L)).thenReturn(Optional.of(employee2));
        var result = employeeService.getEmployeeById(634L);

        Assertions.assertAll("Get employee by id",
                () -> assertTrue(result.isPresent(), "checking..."),
                () -> assertNotNull(result, "checking nulls"),
                () -> assertNotEquals(result, employeeDTO2),
                () -> assertEquals(result, Optional.of(employeeDTO2))
        );
    }

    @Test
    void getEmployeeByIdNotFound() {
        when(employeeRepo.findById(45634L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(45634L));

        String message = "Employee with id " + 45634L + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(message));

    }

    @Test
    void newEmployee() {
        //2 repos con mock
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role(2L, "ROLE_USER")));
        when(employeeRepo.save(employee2)).thenReturn(employee2);
        //service con injectMocks
        employeeService.newEmployee(employee2);

        Assertions.assertAll("New employee",
                () -> assertNotEquals("sergio", employee2.getUsername()),
                () -> assertFalse(employee2.getName().isEmpty()),
                () -> assertEquals(employee2.getLastname(), "rey"),
                () -> verify(employeeRepo, times(1)).save(employee2)
        );
    }

    @Test
    void newEmployeeEmptyRole() {
        when(roleRepository.findByName("")).thenReturn(Optional.empty());
        when(employeeRepo.save(employee2)).thenReturn(employee2);

        Exception exception = assertThrows(FieldValidationException.class, () -> employeeService.newEmployee(employee2));

        String message = "Roles cannot be empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(message));
    }

    @Test
    void updateEmployee() {

        employeeRepo.save(employee2);
        EmployeeDTOMapper.getInstance().setEmployeeDTOMapper(employee3).build();

        Assertions.assertAll("Checking if employee exists",
                () -> assertEquals(634L, (long) employee2.getId_employee()),
                () -> assertFalse(employeeRepo.existsById(634L)), //before using save,
                () -> verify(employeeRepo, times(1)).save(employee2),
                () -> assertEquals(employeeDTO2, EmployeeDTOMapper.getInstance().setEmployeeDTOMapper(employee3).build())
        );
    }

    @Test
    void updateEmployee2() {

        Assertions.assertAll("mapper",
                () -> assertEquals(employeeDTO2, EmployeeDTOMapper.getInstance().setEmployeeDTOMapper(employee2).build())

        );
    }


    @Test
    void updateEmployeeNotFound() {
        when(employeeRepo.findById(23432L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(employee2, 23432L));

        String expectedMessage = "Employee with id " + 23432L + " not found.Nothing to update";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void deleteEmployeeById() {
        when(employeeRepo.findById(634L)).thenReturn(Optional.of(employee2));

        employeeService.deleteEmployeeById(634L);

        verify(employeeRepo, times(1)).deleteById(634L);

    }

    @Test
    void deleteEmployeeByIdNotFound() {

        when(employeeRepo.findById(234112L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployeeById(234112L));

        String expectedMessage = "Employee with id 234112 not found.Nothing to delete";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void deleteAllEmployeesNotEmptyList() {
        when(employeeRepo.findAll()).thenReturn(buildEmployeeList());

        employeeService.deleteAllEmployees();

        verify(employeeRepo, times(1)).deleteAll();


    }

    @Test
    void deleteAllEmployeesEmptyList() {

        when(employeeRepo.findAll()).thenReturn(buildEmptyEmployeeList);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteAllEmployees());

        String message = "error searching for resource";
        String actualMessage = exception.getMessage();

        assertFalse(actualMessage.contains(message));


    }

    //4)

    List<Employee> buildEmptyEmployeeList = new ArrayList<>();

    List<Employee> buildEmployeeList() {
        Employee employee1 = Employee.builder()
                .id_employee(456L)
                .username("alfredo")
                .name("alfredo")
                .lastname("rios")
                .password("12345")
                .email("alfred23@net.com")
                .roles(List.of(new Role(2L, "ROLE_USER")))
                .build();

        return List.of(employee1, employee2);
    }

    Employee employee2 = Employee.builder()
            .id_employee(634L)
            .username("raul")
            .name("raul")
            .lastname("rey")
            .password(passwordBCrypt)
            .email("raulrey534@com.es")
            .roles(List.of(new Role(2L, "ROLE_USER")))
            .build();

    Employee illegalArgumentsEmployee = Employee.builder()
            .id_employee(42513L)
            .username("luisg")
            .name("Luis")
            .lastname("Garcia")
            .email("luisg23@mail.net")
            .password("")
            .roles(List.of(new Role(2L, "ROLE_USER")))
            .build();


    List<EmployeeDTO> buildEmployeeDTOList() {
        EmployeeDTO employeeDTO1 = EmployeeDTO.builder()
                .id_employee(456L)
                .username("alfredo")
                .name("alfredo")
                .lastname("rios")
                .email("alfred23@net.com")
                .build();

        return List.of(employeeDTO1, employeeDTO2);
    }

}