package com.faol.security.dto;

import com.faol.security.entity.Address;
import com.faol.security.entity.Department;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * DTO Company con atributos, getters, setters, equals, hashcode,  constructor vacio
 * constructor con argumentos y builder
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private String companyName;
    private String country;
    private List<Department> departmentList;
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDTO that)) return false;
        return Objects.equals(getCompanyName(), that.getCompanyName())
                && Objects.equals(getCountry(), that.getCountry())
                && Objects.equals(getDepartmentList(), that.getDepartmentList())
                && Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyName(), getCountry(), getDepartmentList(), getAddress());
    }
}
