package com.faol.security.controller;

import com.faol.security.dto.EmployeeDTO;
import com.faol.security.dto.mapper.EmployeeDTOMapper;
import com.faol.security.entity.Employee;
import com.faol.security.service.EmployeeServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        HashMap<String, Object> response = new HashMap<>();
        List<EmployeeDTO> result = service.getAllEmployees();

        if (result.isEmpty()) {
            response.put("message", "EmployeesDTO list is empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("message", "List ok");
            response.put("employeesDTO list", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/get_employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {

        HashMap<String, Object> response = new HashMap<>();
        Optional<EmployeeDTO> employeeDTO = service.getEmployeeById(id);

        if (employeeDTO.isEmpty()) {
            response.put("message", "employee not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("message", "employee found");
            response.put("employeeDTO", employeeDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }


    }

    @PostMapping("/new")
    public ResponseEntity<?> newEmployee(@Valid @RequestBody Employee employee) {

        HashMap<String, Object> response = new HashMap<>();



            service.newEmployee(employee);

            EmployeeDTO employeeDTO = EmployeeDTOMapper.getInstance().setEmployeeDTOMapper(employee).build();

            response.put("message", "Employee created");
            response.put("employeeDTO", employeeDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);

       
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {

        Optional<EmployeeDTO> result = service.getEmployeeById(id);
        HashMap<String, Object> response = new HashMap<>();

        if (result.isPresent()) {

            EmployeeDTO employeeDTO1 = service.updateEmployee(employee, id);
            response.put("message", "employee updated successful");
            response.put("employeeDTO", employeeDTO1);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } else {
            response.put("message", "error updating employee");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {

        HashMap<String, Object> response = new HashMap<>();
        Optional<EmployeeDTO> employeeDTO = service.getEmployeeById(id);

        if (employeeDTO.isEmpty()) {
            response.put("message", "Employee with id " + id + " not found.Nothing to delete");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            service.deleteEmployeeById(id);
            response.put("message", "Employee with id " + id + " deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<?> deleteAllEmployees() {

        HashMap<String, Object> response = new HashMap<>();
        List<EmployeeDTO> employeeDTOList = service.getAllEmployees();

        if (employeeDTOList.isEmpty()) {
            response.put("message", "Empty list.Nothing to delete");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            service.deleteAllEmployees();
            response.put("message", "All employees deleted.Empty list");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }
}
