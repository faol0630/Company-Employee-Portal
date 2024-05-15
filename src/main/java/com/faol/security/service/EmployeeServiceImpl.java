package com.faol.security.service;


import com.faol.security.dto.EmployeeDTO;
import com.faol.security.dto.mapper.EmployeeDTOMapper;
import com.faol.security.entity.Employee;
import com.faol.security.entity.Role;
import com.faol.security.exceptions.FieldValidationException;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.repository.EmployeeRepo;
import com.faol.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeServiceInt {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employeeList = employeeRepo.findAll();

        if (!employeeList.isEmpty()){
            return employeeList.stream().map(employee -> EmployeeDTOMapper
                            .getInstance()
                            .setEmployeeDTOMapper(employee)
                            .build())
                    .collect(Collectors.toList()); //genera una lista de EmployeeDTO
        }else{
            throw new ResourceNotFoundException("Empty list");
        }

    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(Long id_employee) {

        Optional<Employee> employee = employeeRepo.findById(id_employee);

        if (employee.isPresent()) {
            return Optional
                    .of(EmployeeDTOMapper.getInstance()
                            .setEmployeeDTOMapper(employee.orElseThrow())
                            .build());


        } else {
            throw new ResourceNotFoundException("Employee with id " + id_employee + " not found");
        }


    }

    @Override
    public void newEmployee(Employee employee) {
        String passwordBCrypt = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(passwordBCrypt);

        //buscamos el role en la DB:
        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        //creamos una lista de roles debido a la relacion @ManyToMany:
        List<Role> roles = new ArrayList<>();

        if (role.isPresent()) {
            //agregamos el role a la lista de roles del empleado
            roles.add(role.orElseThrow());
        } else {
            throw new FieldValidationException("Roles cannot be empty");
        }
        //agregamos la lista de roles al employee:
        employee.setRoles(roles);

        //errores manejados con FieldValidationException

        if (employee.getName() == null || employee.getName().equals("")) {
            throw new FieldValidationException("Name cannot be empty");
        }
        if (employee.getLastname() == null || employee.getLastname().equals("")) {
            throw new FieldValidationException("Lastname cannot be empty");
        }
        if (employee.getUsername() == null || employee.getUsername().equals("")) {
            throw new FieldValidationException("Username cannot be empty");
        }
        if (employee.getEmail() == null || employee.getEmail().equals("")) {
            throw new FieldValidationException("Email cannot be empty");
        }
        if (employee.getPassword() == null || employee.getPassword().equals("")) {
            throw new FieldValidationException("Password cannot be empty");
        }

        //creamos el newEmployee:
        employeeRepo.save(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Employee employee, Long id_employee) {

        Optional<Employee> founded = employeeRepo.findById(id_employee);

        if (founded.isPresent()) {

            Employee updatedEmployee = Employee.builder()
                    .id_employee(id_employee)
                    .name(employee.getName())
                    .lastname(employee.getLastname())
                    .email(employee.getEmail())
                    .username(employee.getUsername())
                    .password(passwordEncoder.encode(employee.getPassword()))
                    .build();

            Employee employeeToSave = employeeRepo.save(updatedEmployee);

            EmployeeDTO employeeDTO = EmployeeDTOMapper.getInstance().setEmployeeDTOMapper(employeeToSave).build();

            return employeeDTO;

        } else {

            throw new ResourceNotFoundException("Employee with id " + id_employee + " not found.Nothing to update");

        }

    }

    @Override
    public void deleteEmployeeById(Long id_employee) {

        Optional<Employee> employee = employeeRepo.findById(id_employee);

        if (employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee with id " + id_employee + " not found.Nothing to delete");
        } else {
            employeeRepo.deleteById(id_employee);
        }

    }

    @Override
    public void deleteAllEmployees() {

        List<Employee> employeeList = employeeRepo.findAll();

        if (employeeList.isEmpty()) {
            throw new ResourceNotFoundException("Empty list.Nothing to delete");
        } else {
            employeeRepo.deleteAll();

        }

    }


}
