/*
package com.faol.security.auth.filters;

import com.faol.security.entity.Employee;
import com.faol.security.entity.Role;
import com.faol.security.repository.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtAuthFilterTest {

    Employee employee;
    String username;
    String password;
    HttpServletRequest request;
    HttpServletRequest response;
    JwtAuthFilter jwtAuthFilter;
    UsernamePasswordAuthenticationToken authToken;

    @Autowired
    AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {

        request = new MockHttpServletRequest();

        jwtAuthFilter = new JwtAuthFilter(authenticationManager);

        authToken = new UsernamePasswordAuthenticationToken(username, password);

        //employee = null;
        username = null;
        password = null;

        employee = Employee.builder()
                .id_employee(456L)
                .username("alfredo")
                .name("alfredo")
                .lastname("rios")
                .password("12345")
                .email("alfred23@net.com")
                .roles(List.of(new Role(2L, "ROLE_USER")))
                .build();

    }

    @Test
    void attemptAuthentication() throws IOException {

        employee = new ObjectMapper().readValue(request.getInputStream(), Employee.class);
        username = employee.getUsername();
        password = employee.getPassword();

        when(jwtAuthFilter.attemptAuthentication(request, (HttpServletResponse) response)).thenReturn(authenticationManager.authenticate(authToken));

        Assertions.assertAll(
                () -> assertNotNull(authenticationManager.authenticate(authToken))
        );
    }

    @Test
    void successfulAuthentication() {
    }

    @Test
    void unsuccessfulAuthentication() {
    }
}*/
