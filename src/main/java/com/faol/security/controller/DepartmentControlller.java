package com.faol.security.controller;

import com.faol.security.entity.Department;
import com.faol.security.exceptions.*;
import com.faol.security.exceptions.IllegalArgumentException;
import com.faol.security.service.DepartmentServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/department")
@SuppressWarnings("unused")
public class DepartmentControlller {

    @Autowired
    DepartmentServiceInt departmentServiceInt;

    //@GetMapping("/get_all")
    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDepartments() {

        try{

            HashMap<String, Object> response = new HashMap<>();
            List<Department> result = departmentServiceInt.getAllDepartments();

            response.put("message", "List ok");
            response.put("deparment's list", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (ResourceNotFoundException ex){
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    //@GetMapping("/get_department/{id}")
    @RequestMapping(value = "/get_department/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDepartmentById(@PathVariable Integer id) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            Optional<Department> department = departmentServiceInt.getDepartmentById(id);
            response.put("message", "department found");
            response.put("department", department.orElseThrow());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException ex) {

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.entityNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        }catch (IllegalArgumentException e){

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    //@PostMapping("/new")
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> newDepartment(@Valid @RequestBody Department department) {

        try {
            HashMap<String, Object> response = new HashMap<>();

            departmentServiceInt.newDepartment(department);

            response.put("message", "department created");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IllegalArgumentException ex) {

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }catch (DataIntegrityViolationException e){

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.dataIntegrityViolationException(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

    }

    //@PutMapping("/update/{id}")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            Department department1 = departmentServiceInt.updateDepartment(department, id);
            response.put("message", "department updated successfully");
            response.put("department", department1);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.entityNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }catch (IllegalArgumentException e){
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (DataIntegrityViolationException e){
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.dataIntegrityViolationException(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

    }

    //@DeleteMapping("/delete/{id}")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDepartmentById(@PathVariable Integer id) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            departmentServiceInt.deleteDepartmentById(id);
            response.put("message", "department with id " + id + " deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.entityNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        }catch (IllegalArgumentException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }catch (DataIntegrityViolationException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.dataIntegrityViolationException(ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

    }

    //@DeleteMapping("/delete_all")
    @RequestMapping(value = "/delete_all", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllDepartments() {

        try {

            HashMap<String, Object> response = new HashMap<>();
            departmentServiceInt.deleteAllDepartments();
            response.put("message", "All departments deleted.Empty list");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


}
