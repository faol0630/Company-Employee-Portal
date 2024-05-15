package com.faol.security.controller;

import com.faol.security.dto.EmployeeDTO;
import com.faol.security.dto.mapper.EmployeeDTOMapper;
import com.faol.security.entity.Employee;
import com.faol.security.exceptions.ErrorDetails;
import com.faol.security.exceptions.FieldValidationException;
import com.faol.security.exceptions.HandlerExceptionController;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.service.EmployeeServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeServiceInt service;

    @GetMapping("/get_all")
    public ResponseEntity<?> getAllEmployees() {

        try{

            HashMap<String, Object> response = new HashMap<>();
            List<EmployeeDTO> result = service.getAllEmployees();

            response.put("message", "List ok");
            response.put("employeesDTO list", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (ResourceNotFoundException ex){
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @GetMapping("/get_employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            Optional<EmployeeDTO> employeeDTO = service.getEmployeeById(id);
            response.put("message", "employee found");
            response.put("employeeDTO", employeeDTO.orElseThrow());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundException ex) {

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @PostMapping("/new")
    public ResponseEntity<?> newEmployee(@Valid @RequestBody Employee employee) {

        HashMap<String, Object> response = new HashMap<>();

        try {

            service.newEmployee(employee);

            EmployeeDTO employeeDTO = EmployeeDTOMapper.getInstance().setEmployeeDTOMapper(employee).build();

            response.put("message", "Employee created");
            response.put("employeeDTO", employeeDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (FieldValidationException ex) {

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleFieldValidationException(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }


    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            EmployeeDTO employeeDTO1 = service.updateEmployee(employee, id);
            response.put("message", "employee updated successful");
            response.put("employeeDTO", employeeDTO1);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            service.deleteEmployeeById(id);
            response.put("message", "Employee with id " + id + " deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<?> deleteAllEmployees() {

        try {

            HashMap<String, Object> response = new HashMap<>();
            service.deleteAllEmployees();
            response.put("message", "All employees deleted.Empty list");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }
}
