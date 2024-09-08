package com.faol.security.dto.mapper;

import com.faol.security.dto.EmployeeDTO;
import com.faol.security.entity.Employee;


/**
 * Clase que contiene un metodo el cual crea un EmployeDTO a partir de un entity Employee
 * pasado como parametro
 */
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

    /**
     * Metodo que crea un EmployeeDTO a partir de un entity Employee
     * @return
     */
    public EmployeeDTO build(){

        if (employee == null){
            //throw new RuntimeException("You must pass the entity as a parameter");
            throw new IllegalArgumentException("You must pass the entity as a parameter");
        }

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id_employee(employee.getId_employee())
                .name(employee.getName())
                .lastname(employee.getLastname())
                .email(employee.getEmail())
                .username(employee.getUsername())
                .address(employee.getAddress())
                .department(employee.getDepartment())
                .build();

        return employeeDTO;
    }
}
