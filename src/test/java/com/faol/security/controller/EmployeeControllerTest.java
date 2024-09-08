package com.faol.security.controller;

import com.faol.security.auth.TestSecurityConfig;
import com.faol.security.dto.EmployeeDTO;
import com.faol.security.entity.Address;
import com.faol.security.entity.Company;
import com.faol.security.entity.Department;
import com.faol.security.entity.Employee;
import com.faol.security.exceptions.IllegalArgumentException;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.service.EmployeeServiceInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * para este test no se necesita la DB mysql en servicio.
 */

@WebMvcTest(EmployeeController.class)
@Import(TestSecurityConfig.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceInt employeeServiceInt;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private List<EmployeeDTO> employeeDTOList;

    @BeforeEach
    void setUp() {

        employee = Employee.builder()
                .id_employee(45092L)
                .name("Alicia")
                .lastname("Saa")
                .username("alice")
                .password("12345")
                .email("aliciasaa3421@net.com")
                .roles(new ArrayList<>())
                .department(new Department())
                .address(new Address())
                .build();

        employeeDTO = EmployeeDTO.builder()
                .id_employee(45092L)
                .name("Alicia")
                .lastname("Saa")
                .username("alice")
                .email("aliciasaa3421@net.com")
                .department(new Department())
                .address(new Address())
                .build();

        employeeDTOList = new ArrayList<>();
        employeeDTOList.add(employeeDTO);
    }

    @Test
    void getAllEmployees() throws Exception {

        when(employeeServiceInt.getAllEmployees()).thenReturn(employeeDTOList);

        MvcResult result = mockMvc.perform(get("/employee/get_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("List ok")))
                .andExpect(jsonPath("$.employeeDTOList[0].username", is(employeeDTO.getUsername())))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void getAllEmployeesEmptyList() throws Exception {

        when(employeeServiceInt.getAllEmployees()).thenThrow(new ResourceNotFoundException("No employees found"));

        MvcResult result = mockMvc.perform(get("/employee/get_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No employees found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void getEmployeeById() throws Exception {

        long id = 45092L;
        when(employeeServiceInt.getEmployeeById(id)).thenReturn(Optional.of(employeeDTO));

        MvcResult result = mockMvc.perform(get("/employee/get_employee/45092")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("employee found")))
                .andExpect(jsonPath("$.employeeDTO.username").value(employeeDTO.getUsername()))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void getEmployeeByIdNotFound() throws Exception {

        long id = 134;
        when(employeeServiceInt.getEmployeeById(id)).thenThrow(new ResourceNotFoundException("No employees found"));

        MvcResult result = mockMvc.perform(get("/employee/get_employee/134")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No employees found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void newEmployee() throws Exception {

        // JSON representation of the Employee entity
        String employeeJson = "{ " +
                "\"name\": \"Alicia\", " +
                "\"lastname\": \"Saa\", " +
                "\"email\": \"aliciasaa3421@net.com\", " +
                "\"username\": \"alice\", " +
                "\"password\": \"12345\", " +
                "\"department\": { " +
                "    \"id\": 1, " +
                "    \"name\": \"Engineering\"" +
                "}, " +
                "\"roles\": [ " +
                "    { \"id_role\": 1, \"name\": \"ROLE_USER\" }, " +
                "    { \"id_role\": 2, \"name\": \"ROLE_ADMIN\" } " +
                "], " +
                "\"address\": { " +
                "    \"street\": \"123 Main St\", " +
                "    \"number\": \"5\", " +
                "    \"zipcode\": \"12345\" " +
                "} " +
                "}";


        doNothing().when(employeeServiceInt).newEmployee(any(Employee.class));

        mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Employee created")))
                .andExpect(jsonPath("$.employeeDTO.username", is(employeeDTO.getUsername())))
                .andExpect(jsonPath("$.employeeDTO.lastname", is(employeeDTO.getLastname())));


    }

    @Test
    void newEmployeeIllegalArgumentException() throws Exception {

        // JSON representation of the Employee entity
        String employeeJson = "{ " +
                "\"name\": \"Alicia\", " +
                "\"lastname\": \"Saa\", " +
                "\"email\": \"aliciasaa3421@net.com\", " +
                "\"username\": \"alice\", " +
                "\"password\": \"12345\", " +
                "\"department\": { " +
                "    \"id\": 1, " +
                "    \"name\": \"Engineering\"" +
                "}, " +
                "\"roles\": [ " +
                "    { \"id_role\": 1, \"name\": \"ROLE_USER\" }, " +
                "    { \"id_role\": 2, \"name\": \"ROLE_ADMIN\" } " +
                "], " +
                "\"address\": { " +
                "    \"street\": \"123 Main St\", " +
                "    \"number\": \"5\", " +
                "    \"zipcode\": \"12345\" " +
                "} " +
                "}";

        doThrow(new IllegalArgumentException("error validating the field")).when(employeeServiceInt).newEmployee(any(Employee.class));

        MvcResult result = mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.body.error", is("error validating the field")))
                .andExpect(jsonPath("$.body.message", is("error validating the field")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }

    @Test
    void updateEmployee() throws Exception{

        long id = 45092L;

        // JSON representation of the Company entity
        String employeeJson = "{ " +
                "\"name\": \"Alicia\", " +
                "\"lastname\": \"Saa\", " +
                "\"email\": \"aliciasaa3421@net.com\", " +
                "\"username\": \"alice\", " +
                "\"password\": \"12345\", " +
                "\"department\": { " +
                "    \"id\": 1, " +
                "    \"name\": \"Engineering\"" +
                "}, " +
                "\"roles\": [ " +
                "    { \"id_role\": 1, \"name\": \"ROLE_USER\" }, " +
                "    { \"id_role\": 2, \"name\": \"ROLE_ADMIN\" } " +
                "], " +
                "\"address\": { " +
                "    \"street\": \"123 Main St\", " +
                "    \"number\": \"5\", " +
                "    \"zipcode\": \"12345\" " +
                "} " +
                "}";

        when(employeeServiceInt.updateEmployee(any(Employee.class), eq(id))).thenReturn(employeeDTO);

        MvcResult result = mockMvc.perform(put("/employee/update/45092")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("employee updated successfully")))
                .andExpect(jsonPath("$.employeeDTO.name", is(employeeDTO.getName())))
                .andExpect(jsonPath("$.employeeDTO.lastname", is(employeeDTO.getLastname())))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta


    }

    @Test
    void updateEmployeeNotFound() throws Exception{
        long id = 45092111L;

        // JSON representation of the Company entity
        String employeeJson = "{ " +
                "\"name\": \"Alicia\", " +
                "\"lastname\": \"Saa\", " +
                "\"email\": \"aliciasaa3421@net.com\", " +
                "\"username\": \"alice\", " +
                "\"password\": \"12345\", " +
                "\"department\": {}, " +
                "\"roles\": [ " +
                "    { \"id_role\": 1, \"name\": \"ROLE_USER\" }, " +
                "    { \"id_role\": 2, \"name\": \"ROLE_ADMIN\" } " +
                "], " +
                "\"address\": { " +
                "    \"street\": \"123 Main St\", " +
                "    \"number\": \"5\", " +
                "    \"zipcode\": \"12345\" " +
                "} " +
                "}";

        doThrow(new ResourceNotFoundException("Employee not found")).when(employeeServiceInt).updateEmployee(any(Employee.class), eq(id));

        MvcResult result = mockMvc.perform(put("/employee/update/45092111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("Employee not found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta


    }

    @Test
    void deleteEmployeeById() throws Exception{

        long id = 45092L;

        doNothing().when(employeeServiceInt).deleteEmployeeById(id);

        MvcResult result = mockMvc.perform(delete("/employee/delete/45092")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Employee with id 45092 deleted")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void deleteEmployeeByIdNotFound() throws Exception{

        long id = 45092L;

        doThrow(new ResourceNotFoundException("No employees found")).when(employeeServiceInt).deleteEmployeeById(id);

        MvcResult result = mockMvc.perform(delete("/employee/delete/45092")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No employees found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void deleteAllEmployees() throws Exception {

        doNothing().when(employeeServiceInt).deleteAllEmployees();

        MvcResult result = mockMvc.perform(delete("/employee/delete_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("All employees deleted.Empty list")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }

    @Test
    void deleteAllEmployeesEmptyList() throws Exception {

        doThrow(new ResourceNotFoundException("No employees found")).when(employeeServiceInt).deleteAllEmployees();

        MvcResult result = mockMvc.perform(delete("/employee/delete_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No employees found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }
}