package com.faol.security.auth;

import com.faol.security.entity.Employee;
import com.faol.security.entity.Role;
import com.faol.security.repository.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class JpaUserDetailsServiceTest {

    Employee employee1;
    List<GrantedAuthority> authorities;
    User user;

    @Mock
    EmployeeRepo employeeRepo;

    @InjectMocks
    JpaUserDetailsService jpaUserDetailsService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        employee1 = Employee.builder()
                .id_employee(456L)
                .username("alfredo")
                .name("alfredo")
                .lastname("rios")
                .password("12345")
                .email("alfred23@net.com")
                .roles(List.of(new Role(2L, "ROLE_USER")))
                .build();

        authorities = employee1
                .getRoles()
                .stream()
                .map( role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());

        user = new User(//este User viene con spring framework.No es un entity que hayamos creado.
                employee1.getUsername(),
                employee1.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );

        employeeRepo.save(employee1);


    }

    @Test
    void loadUserByUsername() {

        String username = "luis";

        when(employeeRepo.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows( UsernameNotFoundException.class, () -> {
            jpaUserDetailsService.loadUserByUsername(username);
        });

        String actualMessage = exception.getMessage();
        String message = username + " not found";

        assertTrue(actualMessage.contains(message));


    }

    @Test
    void loadUserByUsernameIsPresentTest() {

        String username = "alfredo";

        when(employeeRepo.findByUsername(username)).thenReturn(Optional.of(employee1));

        var result = jpaUserDetailsService.loadUserByUsername(username);

        Assertions.assertAll(
                () -> assertTrue(result.isEnabled()),
                () -> assertNotNull(result),
                () -> assertDoesNotThrow( () -> jpaUserDetailsService.loadUserByUsername(username))
        );

    }
}