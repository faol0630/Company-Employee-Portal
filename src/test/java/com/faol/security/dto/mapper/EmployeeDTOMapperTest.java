package com.faol.security.dto.mapper;

import com.faol.security.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDTOMapperTest {

    @Test
    void build() {
        //given
        Employee employeeNull = null;
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            EmployeeDTOMapper.getInstance().setEmployeeDTOMapper(employeeNull).build();
        });

    }
}