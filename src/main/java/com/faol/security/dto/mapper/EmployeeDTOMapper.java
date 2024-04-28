package com.faol.security.dto.mapper;

import com.faol.security.dto.EmployeeDTO;
import com.faol.security.entity.Employee;

public class EmployeeDTOMapper {

    //patron builder (sin usar lombok)

    //2) instancia static y private de esta clase:
    //private static EmployeeDTOMapper employeeDTOMapperInstance;

    //4) declaracion del entity:
    private Employee employee;

    //1) private constructor:
    private EmployeeDTOMapper(){

    }

    //3) metodo que obtiene instancia de esta clase:(hasta aca es un patron singleton)
    public static EmployeeDTOMapper getInstance(){
        return new EmployeeDTOMapper();
    }

    //5) metodo set del entity modificado para que return un objeto de esta clase:
    public EmployeeDTOMapper setEmployeeDTOMapper(Employee employee) {
        this.employee = employee;
        return this;
    }

    //6) metodo que devuelve un DTO:
    public EmployeeDTO build(){

        if (employee == null){
            throw new RuntimeException("You must pass the entity as a parameter");
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId_employee(employee.getId_employee());
        employeeDTO.setName(employee.getName());
        employeeDTO.setLastname(employee.getLastname());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setUsername(employee.getUsername());

        return employeeDTO;
    }
}
