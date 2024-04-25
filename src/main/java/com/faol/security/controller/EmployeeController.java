package com.faol.security.controller;

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
        List<Employee> result = service.getAllEmployees();

        if (result.isEmpty()) {
            response.put("message", "Employees list is empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("message", "List ok");
            response.put("employees list", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/get_employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {

        HashMap<String, Object> response = new HashMap<>();
        Optional<Employee> employee = service.getEmployeeById(id);

        if (employee.isEmpty()) {
            response.put("message", "employee not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("message", "employee found");
            response.put("employee", employee);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }


    }

    @PostMapping("/new")
    public ResponseEntity<?> newEmployee(@Valid @RequestBody Employee employee) {

        HashMap<String, Object> response = new HashMap<>();
        service.newEmployee(employee);

        response.put("message", "Employee created");
        response.put("employee", employee);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){

        Optional<Employee> result = service.getEmployeeById(id);
        HashMap<String, Object> response = new HashMap<>();

        if (result.isPresent()){

            Employee employee1 = service.updateEmployee(employee, id);
            response.put("message", "employee updated successful");
            response.put("employee", employee1);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }else{
            response.put("message", "error updating employee");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {

        HashMap<String, Object> response = new HashMap<>();
        Optional<Employee> employee = service.getEmployeeById(id);

        if (employee.isEmpty()){
            response.put("message", "Employee not found.Nothing to delete");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }else{
            service.deleteEmployeeById(id);
            response.put("message", "Employee deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<?> deleteAllEmployees() {

        HashMap<String, Object> response = new HashMap<>();
        List<Employee> employeeList = service.getAllEmployees();

        if (employeeList.isEmpty()){
            response.put("message", "Empty list.Nothing to delete");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }else{
            service.deleteAllEmployees();
            response.put("message", "All employees deleted.Empty list");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }
}
