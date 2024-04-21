package com.faol.security.service;


import com.faol.security.entity.Employee;
import com.faol.security.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeServiceInt{

    @Autowired
    EmployeeRepo employeeRepo;

    @Override
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id_employee){

         return employeeRepo.findById(id_employee);

    }

    @Override
    public void newEmployee(Employee employee){
        employeeRepo.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id_employee){
       Employee founded = employeeRepo.findById(id_employee).get();

       Employee updatedEmployee = Employee.builder()
               .id_employee(id_employee)
               .name(employee.getName())
               .lastname(employee.getLastname())
               .email(employee.getEmail())
               .username(employee.getUsername())
               .password(employee.getPassword())
               .build();

       employeeRepo.save(updatedEmployee);

       return updatedEmployee;

    }

    @Override
    public void deleteEmployeeById(Long id_employee){

        employeeRepo.deleteById(id_employee);
    }

    @Override
    public void deleteAllEmployees(){
        employeeRepo.deleteAll();
    }


}
