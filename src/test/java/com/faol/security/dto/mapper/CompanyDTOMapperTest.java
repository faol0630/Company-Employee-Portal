package com.faol.security.dto.mapper;

import com.faol.security.entity.Company;
import com.faol.security.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDTOMapperTest {

    @Test
    void build() {
        //given
        Company companyNull = null;
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            CompanyDTOMapper.getInstance().setCompanyDTOMapper(companyNull).build();
        });

    }
}