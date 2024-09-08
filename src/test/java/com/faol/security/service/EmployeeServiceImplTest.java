package com.faol.security.service;

import com.faol.security.dto.EmployeeDTO;
import com.faol.security.dto.mapper.EmployeeDTOMapper;
import com.faol.security.entity.Company;
import com.faol.security.entity.Employee;
import com.faol.security.entity.Role;
import com.faol.security.exceptions.FieldValidationException;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.repository.EmployeeRepo;
import com.faol.security.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmployeeServiceImplTest {

    //1) declarar mock
    //lo que en el service se inyecta con @Autowired, aca se le hace @Mock
    @MockBean
    EmployeeRepo employeeRepo;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    RoleRepository roleRepository;
    //-----------------

    //2) declarar clase a la que se le inyectan los mocks (la clase que se está probando)
    @InjectMocks
    EmployeeServiceImpl employeeService;
    //---------------


    //3) declaraciones.
    String passwordBCrypt;
    EmployeeDTO employeeDTO1;
    EmployeeDTO employeeDTO2;
    Employee employee1;
    Employee employee2;
    List<Employee> buildEmptyEmployeeList;
    List<Employee> employeeList;
    List<EmployeeDTO> employeeDTOList;


    //4) instancias:
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        passwordBCrypt = passwordEncoder.encode("12345");

        employeeDTO1 = EmployeeDTO.builder()
                .id_employee(456L)
                .username("alfredo")
                .name("alfredo")
                .lastname("rios")
                .email("alfred23@net.com")
                .build();

        employeeDTO2 = EmployeeDTO.builder()
                .id_employee(634L)
                .username("raul")
                .name("raul")
                .lastname("rey")
                .email("raulrey534@com.es")
                .build();

        employee1 = Employee.builder()
                .id_employee(456L)
                .username("alfredo")
                .name("alfredo")
                .lastname("rios")
                .password("12345")
                .email("alfred23@net.com")
                .roles(List.of(new Role(2L, "ROLE_USER")))
                .build();

        employee2 = Employee.builder()
                .id_employee(634L)
                .username("raul")
                .name("raul")
                .lastname("rey")
                .password(passwordBCrypt)
                .email("raulrey534@com.es")
                .roles(List.of(new Role(2L, "ROLE_USER")))
                .build();

        buildEmptyEmployeeList = new ArrayList<>();

        employeeList = List.of(employee1, employee2);

        employeeDTOList = List.of(employeeDTO1, employeeDTO2);

    }

    @Test
    void getAllEmployees() {
        //5) dentro de un test, definiendo comportamiento del mock:
        when(employeeRepo.findAll()).thenReturn(employeeList); // employeeList (DTO se aplica en el service)
        var result = employeeService.getAllEmployees(); //employeeDTOList

        Assertions.assertAll("Employees test",
                () -> assertFalse(result.isEmpty(), "checking size"),
                () -> assertNotNull(result, "checking nulls"),
                () -> assertNotEquals(result, employeeList),
                () -> assertEquals(result, employeeDTOList),
                () -> assertDoesNotThrow( () -> employeeService.getAllEmployees())

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
                () -> assertEquals(result, Optional.of(employeeDTO2)),
                () -> assertDoesNotThrow( () -> employeeService.getEmployeeById(634L))
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
                () -> verify(employeeRepo, times(1)).save(employee2),
                () -> assertDoesNotThrow( () -> employeeService.newEmployee(employee2))
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

        Long id = 634L;
        when(employeeRepo.findById(id)).thenReturn(Optional.of(employee2));
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee2);

        EmployeeDTO result = employeeService.updateEmployee(employee2, id);

        Assertions.assertAll("Update employee test",
                () -> assertNotNull(result),
                () -> assertEquals(employee2.getId_employee(), result.getId_employee()),
                () -> assertEquals(employee2.getUsername(), result.getUsername()),
                () -> verify(employeeRepo, times(1)).findById(id),
                () -> verify(employeeRepo, times(1)).save(any(Employee.class)),
                () -> assertDoesNotThrow( () -> employeeService.updateEmployee(employee2, id))
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

        Assertions.assertAll(
                () -> verify(employeeRepo, times(1)).deleteById(634L),
                () -> assertDoesNotThrow( () -> employeeService.deleteEmployeeById(634L))
        );



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
        when(employeeRepo.findAll()).thenReturn(employeeList);

        employeeService.deleteAllEmployees();

        Assertions.assertAll(
                () -> verify(employeeRepo, times(1)).deleteAll(),
                () -> assertDoesNotThrow( () -> employeeService.deleteAllEmployees())
        );




    }

    @Test
    void deleteAllEmployeesEmptyList() {

        when(employeeRepo.findAll()).thenReturn(buildEmptyEmployeeList);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteAllEmployees());

        String message = "error searching for resource";
        String actualMessage = exception.getMessage();

        assertFalse(actualMessage.contains(message));

    }

}