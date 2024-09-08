package com.faol.security.controller;

import com.faol.security.auth.TestSecurityConfig;
import com.faol.security.dto.CompanyDTO;
import com.faol.security.entity.Company;
import com.faol.security.exceptions.IllegalArgumentException;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.service.CompanyServiceInt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * para este test no se necesita la DB mysql en servicio.
 */

@WebMvcTest(CompanyController.class)
@Import(TestSecurityConfig.class)
class CompanyControllerTest {

    @Autowired //1)
    private MockMvc mockMvc;

    @MockBean //2)
    private CompanyServiceInt companyServiceInt;

    private Company company;
    private CompanyDTO companyDTO;
    private List<CompanyDTO> companyDTOList;

    @BeforeEach
    void setUp() {

        company = Company.builder()
                .company_id(1)
                .company_name("Test Company")
                .country("Test Country")
                .address(null)
                .departmentList(Collections.emptyList())
                .build();

        companyDTO = CompanyDTO.builder()
                .departmentList(Collections.emptyList())
                .companyName("Test Company")
                .country("Test Country")
                .address(null)
                .build();

        companyDTOList = new ArrayList<>();
        companyDTOList.add(companyDTO);
    }

    @Test
    @WithMockUser
    void getAllCompaniesTest() throws Exception{
        when(companyServiceInt.getAllCompanies()).thenReturn(companyDTOList);

        MvcResult result = mockMvc.perform(get("/company/get_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("List ok")))
                .andExpect(jsonPath("$.companyDTOList[0].country", is(companyDTO.getCountry())))
                .andReturn();

        Assertions.assertAll(
                () -> verify(companyServiceInt, times(1)).getAllCompanies(),
                () -> assertDoesNotThrow( () -> companyServiceInt.getAllCompanies()),
                () -> assertEquals( companyDTOList.get(0).getCountry(), companyDTO.getCountry())
        );

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }

    @Test
    @WithMockUser
    void getAllCompaniesTestEmptyList() throws Exception{
        when(companyServiceInt.getAllCompanies()).thenThrow(new ResourceNotFoundException("No companies found"));

        MvcResult result = mockMvc.perform(get("/company/get_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No companies found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        Assertions.assertAll(
                () -> verify(companyServiceInt, times(1)).getAllCompanies(),
                () -> assertThrows( ResourceNotFoundException.class, () -> companyServiceInt.getAllCompanies())
        );

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void getCompanyById() throws Exception{
        int id = 1;
        when(companyServiceInt.getCompanyById(id)).thenReturn(Optional.of(companyDTO));

        MvcResult result = mockMvc.perform(get("/company/get_company/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("company found")))
                .andExpect(jsonPath("$.companyDTO.country").value(companyDTO.getCountry()))
                .andReturn();

        Assertions.assertAll(
                () -> verify(companyServiceInt, times(1)).getCompanyById(id),
                () -> assertDoesNotThrow( () -> companyServiceInt.getCompanyById(id)),
                () -> assertEquals(companyDTO.getCountry(), company.getCountry())
        );

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void getCompanyByIdNotFound() throws Exception{
        int id = 134;
        when(companyServiceInt.getCompanyById(id)).thenThrow(new ResourceNotFoundException("No companies found"));

        MvcResult result = mockMvc.perform(get("/company/get_company/134")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No companies found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        Assertions.assertAll(
                () -> verify(companyServiceInt, times(1)).getCompanyById(id),
                () -> assertThrows( ResourceNotFoundException.class, () -> companyServiceInt.getCompanyById(id))
        );

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    @WithMockUser
    void newCompany() throws Exception{

        // JSON representation of the Company entity
        String companyJson = "{ " +
                "\"company_name\": \"Test Company\", " +
                "\"country\": \"Test Country\", " +
                "\"address\": { " +
                "    \"street\": \"123 Test St\", " +
                "    \"city\": \"Test City\", " +
                "    \"zipCode\": \"12345\"" +
                "}," +
                "\"departmentList\": [" +
                "    {" +
                "        \"department_id\": 1," +
                "        \"department_name\": \"HR\"" +
                "    }," +
                "    {" +
                "        \"department_id\": 2," +
                "        \"department_name\": \"Engineering\"" +
                "    }" +
                "]" +
                "}";


        doNothing().when(companyServiceInt).newCompany(any(Company.class));

        mockMvc.perform(post("/company/new")
                .contentType(MediaType.APPLICATION_JSON)
                                .content(companyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Company created")))
                .andExpect(jsonPath("$.companyDTO.country", is(companyDTO.getCountry())))
                .andExpect(jsonPath("$.companyDTO.companyName", is(company.getCompany_name())));
    }

    @Test
    @WithMockUser
    void newCompanyIllegalArgumentException() throws Exception{

        // JSON representation of the Company entity
        String companyJson = "{ " +
                "\"company_name\": \"Test Company\", " +
                "\"country\": \"Test Country\", " +
                "\"address\": { " +
                "    \"street\": \"Test Street\", " +
                "    \"city\": \"Test City\", " +
                "    \"zipCode\": \"12345\"" +
                "}," +
                "\"departmentList\": []" +
                "}";

        doThrow(new IllegalArgumentException("error validating the field")).when(companyServiceInt).newCompany(any(Company.class));

        MvcResult result = mockMvc.perform(post("/company/new")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.body.error", is("error validating the field")))
                .andExpect(jsonPath("$.body.message", is("error validating the field")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void updateCompany() throws Exception{

        int id = 1;

        String companyJson = "{ " +
                "\"company_name\": \"Updated Company\", " +
                "\"country\": \"Updated Country\", " +
                "\"address\": { " +
                "    \"street\": \"Test Street\", " +
                "    \"city\": \"Test City\", " +
                "    \"zipCode\": \"12345\"" +
                "}," +
                "\"departmentList\": []" +
                "}";

        when(companyServiceInt.updateCompany(any(Company.class), eq(id))).thenReturn(companyDTO);

        MvcResult result = mockMvc.perform(put("/company/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("company updated successfully")))
                .andExpect(jsonPath("$.companyDTO.country", is(companyDTO.getCountry())))
                .andExpect(jsonPath("$.companyDTO.companyName", is(company.getCompany_name())))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }

    @Test
    void updateCompanyNotFound() throws Exception{

        int id = 13132;

        String companyJson = "{ " +
                "\"company_name\": \"Updated Company\", " +
                "\"country\": \"Updated Country\", " +
                "\"address\": { " +
                "    \"street\": \"Test Street\", " +
                "    \"city\": \"Test City\", " +
                "    \"zipCode\": \"12345\"" +
                "}," +
                "\"departmentList\": []" +
                "}";

        doThrow(new ResourceNotFoundException("Company not found")).when(companyServiceInt).updateCompany(any(Company.class), eq(id));

        MvcResult result = mockMvc.perform(put("/company/update/13132")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("Company not found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }

    @Test
    void deleteCompanyById() throws Exception{
        int id = 1;
        doNothing().when(companyServiceInt).deleteCompanyById(id);

        MvcResult result = mockMvc.perform(delete("/company/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Company with id 1 deleted")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }

    @Test
    void deleteCompanyByIdNotFound() throws Exception{
        int id = 31425;
        doThrow(new ResourceNotFoundException("No companies found")).when(companyServiceInt).deleteCompanyById(id);

        MvcResult result = mockMvc.perform(delete("/company/delete/31425")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No companies found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }

    @Test
    void deleteAllCompanies() throws  Exception{
        doNothing().when(companyServiceInt).deleteAllCompanies();

        MvcResult result = mockMvc.perform(delete("/company/delete_all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("All companies deleted.Empty list")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta

    }

    @Test
    void deleteAllCompaniesEmptyList() throws  Exception{
        doThrow(new ResourceNotFoundException("No companies found")).when(companyServiceInt).deleteAllCompanies();

        MvcResult result = mockMvc.perform(delete("/company/delete_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.message", is("No companies found")))
                .andExpect(jsonPath("$.body.error", is("error searching for resource")))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);  // Imprime el JSON de la respuesta
    }
}
