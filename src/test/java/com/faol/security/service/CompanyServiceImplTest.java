package com.faol.security.service;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.entity.*;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.repository.CompanyRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CompanyServiceImplTest {

    Company company;
    List<Company> companyList;
    Department department;
    List<Department> departmentList;
    Employee employee;
    List<Employee> employeeList;
    Address address;
    CompanyDTO companyDTO;
    List<CompanyDTO> companyDTOList;

    @MockBean
    CompanyRepo companyRepoMock;

    @InjectMocks
    CompanyServiceImpl companyServiceImpl;

    @BeforeEach
    void init() {

        MockitoAnnotations.openMocks(this);

        address = Address.builder()
                .address_id(35241)
                .number("3")
                .street("Vereda 1")
                .zipcode("34561")
                .build();

        department = Department.builder()
                .department_id(3452)
                .deparment_name("Logistica")
                .company(company)
                .employeeList(employeeList)
                .build();

        departmentList = new ArrayList<>();
        departmentList.add(department);

        company = Company.builder()
                .company_id(5477)
                .company_name("H&P")
                .country("USA")
                .departmentList(departmentList)
                .address(address)
                .build();

        companyList = new ArrayList<>();
        companyList.add(company);

        companyDTO = CompanyDTO.builder()
                .companyName(company.getCompany_name())
                .country(company.getCountry())
                .address(company.getAddress())
                .departmentList(company.getDepartmentList())
                .build();

        companyDTOList = new ArrayList<>();
        companyDTOList.add(companyDTO);

        employee = Employee.builder()
                .id_employee(456L)
                .username("alfredo")
                .name("alfredo")
                .lastname("rios")
                .password("12345")
                .email("alfred23@net.com")
                .roles(List.of(new Role(2L, "ROLE_USER")))
                .build();

        employeeList = new ArrayList<>();
        employeeList.add(employee);

    }

    @Test
    void getAllCompanies() {
        when(companyRepoMock.findAll()).thenReturn(companyList); //company
        var result = companyServiceImpl.getAllCompanies(); //companyDTO
        Assertions.assertAll("Company service test",
                () -> assertFalse(result.isEmpty(), "checking size"),
                () -> assertNotNull(result, "checking nulls"),
                () -> assertNotEquals(result, companyList),
                () -> assertEquals(result, companyDTOList),
                () -> assertEquals(result.size(), companyDTOList.size()),
                () -> assertDoesNotThrow(() -> companyServiceImpl.getAllCompanies())

        );
    }

    @Test
    void getAllCompaniesEmptyList() {

        List<Company> emptyCompanyList = new ArrayList<>();
        when(companyRepoMock.findAll()).thenReturn(emptyCompanyList);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> companyServiceImpl.getAllCompanies());

        String message = "error searching for resource";
        String actualMessage = exception.getMessage();

        Assertions.assertAll(
                () -> assertFalse(actualMessage.contains(message)),
                () -> assertEquals(companyRepoMock.findAll().size(), 0)
        );

    }

    @Test
    void getCompanyById() {
        int id = 5477;
        when(companyRepoMock.findById(id)).thenReturn(Optional.of(company));
        var result = companyServiceImpl.getCompanyById(id);
        Assertions.assertAll("Company service test",
                () -> assertFalse(result.isEmpty(), "checking size"),
                () -> assertNotNull(result, "checking nulls"),
                () -> assertNotEquals(result, companyDTO),
                () -> assertEquals(result, Optional.of(companyDTO)),
                () -> assertDoesNotThrow(() -> companyServiceImpl.getCompanyById(id))

        );
    }

    @Test
    void getCompanyByIdNotFound() {

        int id = 45634;
        when(companyRepoMock.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> companyServiceImpl.getCompanyById(id));

        String message = "Company with id " + id + " not found";
        String actualMessage = exception.getMessage();

        Assertions.assertAll(
                () -> assertTrue(actualMessage.contains(message)),
                () -> assertTrue(companyRepoMock.findById(id).isEmpty())
        );

    }

    @Test
    void newCompany() {
        when(companyRepoMock.save(company)).thenReturn(company);
        companyServiceImpl.newCompany(company);
        Assertions.assertAll("New company",
                () -> assertNotEquals("water.S.A", company.getCompany_name()),
                () -> assertFalse(company.getCompany_name().isEmpty()),
                () -> assertEquals(company.getCountry(), "USA"),
                () -> verify(companyRepoMock, times(1)).save(company),
                () -> assertDoesNotThrow(() -> companyServiceImpl.newCompany(company))
        );
    }

    @Test
    void updateCompany() {

        int id = 5477;
        when(companyRepoMock.findById(id)).thenReturn(Optional.of(company));
        when(companyRepoMock.save(any(Company.class))).thenReturn(company);

        CompanyDTO result = companyServiceImpl.updateCompany(company, id);

        Assertions.assertAll("update company test",
                () -> assertNotNull(result),
                () -> assertEquals(company.getCompany_name(), result.getCompanyName()),
                () -> assertEquals(company.getCountry(), result.getCountry()),
                () -> verify(companyRepoMock, times(1)).findById(id),
                () -> verify(companyRepoMock, times(1)).save(any(Company.class)),
                () -> assertDoesNotThrow(() -> companyServiceImpl.updateCompany(company, id))

        );
    }

    @Test
    void updateCompanyNotFounded() {

        int id = 54771234;
        when(companyRepoMock.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> companyServiceImpl.updateCompany(company, id));

        String message = "Company with id " + id + " not found";
        String actualMessage = exception.getMessage();

        Assertions.assertAll("update company test",
                () -> verify(companyRepoMock, times(1)).findById(id),
                () -> assertTrue(actualMessage.contains(message))
        );
    }

    @Test
    void deleteCompanyById() {
        int id = 5477;
        when(companyRepoMock.findById(id)).thenReturn(Optional.of(company));
        companyServiceImpl.deleteCompanyById(id);

        Assertions.assertAll(
                () -> verify(companyRepoMock, times(1)).deleteById(id),
                () -> assertDoesNotThrow(() -> companyServiceImpl.deleteCompanyById(id))

        );

    }

    @Test
    void deleteCompanyByIdNotFound() {
        int id = 547768;
        when(companyRepoMock.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> companyServiceImpl.deleteCompanyById(id));

        String expectedMessage = "Company with id " + id + " not found.Nothing to delete";
        String actualMessage = exception.getMessage();

        Assertions.assertAll(
                () -> assertTrue(actualMessage.contains(expectedMessage)),
                () -> assertTrue(companyRepoMock.findById(id).isEmpty())
        );


    }

    @Test
    void deleteAllCompanies() {
        when(companyRepoMock.findAll()).thenReturn(companyList);
        companyServiceImpl.deleteAllCompanies();

        Assertions.assertAll(
                () -> verify(companyRepoMock, times(1)).deleteAll(),
                () -> assertDoesNotThrow(() -> companyServiceImpl.deleteAllCompanies()),
                () -> assertEquals(companyRepoMock.findAll().size(), 1)
        );

    }

    @Test
    void deleteAllCompaniesEmptyList() {

        List<Company> emptyCompanyList = new ArrayList<>();
        when(companyRepoMock.findAll()).thenReturn(emptyCompanyList);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> companyServiceImpl.deleteAllCompanies());

        String message = "error searching for resource";
        String actualMessage = exception.getMessage();

        Assertions.assertAll(
                () -> assertFalse(actualMessage.contains(message)),
                () -> assertEquals(companyRepoMock.findAll().size(), 0)
        );

    }

}