package com.faol.security.dto;

import com.faol.security.entity.Address;
import com.faol.security.entity.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDTOTest {

    CompanyDTO companyDTO = new CompanyDTO();

    @Test
    void setCompanyName() {
        //given
        String name = "Cocacola company";
        //when
        companyDTO.setCompanyName(name);
        //then
        Assertions.assertAll( "CompanyDTO setters test",
                () -> assertNotNull(companyDTO.getCompanyName()),
                () -> assertInstanceOf(String.class, companyDTO.getCompanyName()),
                () -> assertEquals(name, companyDTO.getCompanyName())
        );
    }

    @Test
    void setCountry() {
        //given
        String country = "USA";
        //when
        companyDTO.setCountry(country);
        //then
        Assertions.assertAll( "CompanyDTO setters test",
                () -> assertNotNull(companyDTO.getCountry()),
                () -> assertInstanceOf(String.class, companyDTO.getCountry()),
                () -> assertEquals(country, companyDTO.getCountry())
        );
    }

    @Test
    void setDepartmentList() {
        List<Department> departmentList = new ArrayList<>();
        companyDTO.setDepartmentList(departmentList);
        Assertions.assertAll( "CompanyDTO setters test",
                () -> assertNotNull(companyDTO.getDepartmentList()),
                () -> assertInstanceOf(java.util.ArrayList.class, companyDTO.getDepartmentList()),
                () -> assertEquals(departmentList, companyDTO.getDepartmentList())
        );
    }

    @Test
    void setAddress() {
        //given
        Address address = new Address();
        //when
        companyDTO.setAddress(address);
        //then
        Assertions.assertAll( "CompanyDTO setters test",
                () -> assertNotNull(companyDTO.getAddress()),
                () -> assertInstanceOf(Address.class, companyDTO.getAddress()),
                () -> assertEquals(address, companyDTO.getAddress())
        );
    }

    @Test
    void hashCodeConsistencyTest(){

        Address address = new Address(4531, "Main St", "2","12345");
        Department department = new Department(1, "HR", null, null);
        List<Department> departments = List.of(department);

        CompanyDTO companyDTO = new CompanyDTO("Company A", "USA", departments, address);

        int initialHashCode = companyDTO.hashCode();
        int subSequentHashCode = companyDTO.hashCode();

        assertEquals(initialHashCode, subSequentHashCode, "Hash code should be consistent");


    }

    @Test
    void equalsSameObjectTest(){

        CompanyDTO companyDTO = CompanyDTO.builder()
                .companyName("123S")
                .address(new Address())
                .departmentList(new ArrayList<>())
                .country("Spain")
                .build();

        assertTrue(companyDTO.equals(companyDTO));

    }

    @Test
    void companyDTOBuilderToStringTest(){

        CompanyDTO.CompanyDTOBuilder builder = CompanyDTO.builder()
                .companyName("Abc.S.A")
                .address(new Address())
                .country("Spain")
                .departmentList(new ArrayList<>());

        String builderToString = builder.toString();

        Assertions.assertAll(
                () -> assertDoesNotThrow( () -> builder.country("Spain")),
                () -> assertInstanceOf(CompanyDTO.CompanyDTOBuilder.class, builder),
                ()-> assertNotNull(builderToString)
        );
    }
}