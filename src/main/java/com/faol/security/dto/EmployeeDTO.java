package com.faol.security.dto;

import com.faol.security.entity.Address;
import com.faol.security.entity.Department;
import lombok.*;

import java.util.Objects;

/**
 * DTO Employee con atributos, getters, setters, equals, hashcode,  constructor vacio
 * constructor con argumentos y builder
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDTO {


    private Long id_employee;

    private String name;

    private String lastname;

    private String email;

    private String username;

    private Address address;

    private Department department;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDTO that)) return false;
        return Objects.equals(getId_employee(), that.getId_employee())
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getLastname(), that.getLastname())
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getUsername(), that.getUsername())
                && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getDepartment(), that.getDepartment()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_employee(), getName(), getLastname(), getEmail(), getUsername(), getAddress(), getDepartment());
    }
}
