package com.faol.security.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    Company company = new Company();

    @Test
    void allArgsConstructorTest() {
        // Arrange
        Address address = new Address();
        List<Department> departmentList = new ArrayList<>();

        // Act
        Company company  = Company.builder()
                .company_id(4361)
                .company_name("Ventanas.S.A")
                .address(address)
                .departmentList(departmentList)
                .country("Spain")
                .build();

        // Assert
        assertNotNull(company);
        assertEquals(4361, company.getCompany_id());
        assertEquals("Ventanas.S.A", company.getCompany_name());
        assertEquals(address, company.getAddress());
        assertEquals("Spain", company.getCountry());
        assertEquals(departmentList, company.getDepartmentList());
    }

    @Test
    void setCompany_id() {
        //given
        int id = 28192;
        //when
        company.setCompany_id(id);
        //then
        Assertions.assertAll( "Company setters test",
                () -> assertNotNull(company.getCompany_id()),
                () -> assertEquals(id, company.getCompany_id()),
                () -> assertInstanceOf(Integer.class, company.getCompany_id()),
                () -> assertDoesNotThrow( () ->  company.setCompany_id(id))
        );
    }

    @Test
    void setCompany_name() {
        //given
        String name = "Cocacola company";
        //when
        company.setCompany_name(name);
        //then
        Assertions.assertAll( "Company setters test",
                () -> assertNotNull(company.getCompany_name()),
                () -> assertEquals(name, company.getCompany_name()),
                () -> assertInstanceOf(String.class, company.getCompany_name()),
                () -> assertDoesNotThrow( () ->  company.setCompany_name(name))
        );
    }

    @Test
    void setCountry() {
        //given
        String country = "USA";
        //when
        company.setCountry(country);
        //then
        Assertions.assertAll( "Company setters test",
                () -> assertNotNull(company.getCountry()),
                () -> assertEquals(country, company.getCountry()),
                () -> assertInstanceOf(String.class, company.getCountry()),
                () -> assertDoesNotThrow( () ->  company.setCountry(country))
        );
    }

    @Test
    void setDepartmentList() {
        List<Department> departmentList = new ArrayList<>();
        company.setDepartmentList(departmentList);
        Assertions.assertAll( "Company setters test",
                () -> assertNotNull(company.getDepartmentList()),
                () -> assertEquals(departmentList, company.getDepartmentList()),
                () -> assertInstanceOf(java.util.ArrayList.class, company.getDepartmentList()),
                () -> assertDoesNotThrow( () ->  company.setDepartmentList(departmentList))
        );
    }

    @Test
    void setAddress() {
        Address address = new Address();
        company.setAddress(address);
        Assertions.assertAll( "Company setters test",
                () -> assertNotNull(company.getAddress()),
                () -> assertEquals(address, company.getAddress()),
                () -> assertInstanceOf(Address.class, company.getAddress()),
                () -> assertDoesNotThrow( () ->  company.setAddress(address))
        );
    }

    @Test
    void companyToStringTest(){
        Company company1 = Company.builder()
                .company_id(44535)
                .company_name("VPN")
                .address(new Address())
                .country("USA")
                .departmentList(new ArrayList<>())
                .build();

        String result = company1.toString();
        String expected = "Company{" +
                "company_id=" + company1.getCompany_id() +
                ", company_name='" + company1.getCompany_name() + '\'' +
                ", country='" + company1.getCountry() + '\'' +
                //", departmentList=" + company1.getDepartmentList() +
                ", address=" + company1.getAddress() +
                '}';

        assertEquals(expected, result);
    }

    @Test
    void builderToStringTest(){

        Address address = new Address();
        List<Department> departmentList = new ArrayList<>();

        Company.CompanyBuilder builder = Company.builder()
                .company_id(4361)
                .company_name("InfoJobSpain")
                .country("Spain")
                .address(address)
                .departmentList(departmentList);

        String builderToString = builder.toString();

        Assertions.assertAll(
                () -> assertNotNull(builderToString),
                () -> assertDoesNotThrow(builder::toString),
                () -> assertInstanceOf(Company.CompanyBuilder.class, builder)
        );
    }

}