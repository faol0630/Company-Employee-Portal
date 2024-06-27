package com.faol.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_employee;

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "lastname must not be null")
    private String lastname;

    @Email(message = "invalid email format")
    @NotNull(message = "email must not be null")
    private String email;

    @NotNull(message = "username must not be null")
    private String username;

    @NotNull(message = "password must not be null")
    private String password;

    @ManyToMany
    @JoinTable(name = "employees_roles",
            joinColumns = @JoinColumn(name = "id_employee"), //esta tabla
            inverseJoinColumns = @JoinColumn(name = "id_role"),
            uniqueConstraints = { @UniqueConstraint(columnNames = { "id_employee", "id_role"})}
    )
    private List<Role> roles;

    @Override
    public String toString() {
        return "Employee{" +
                "id_employee=" + id_employee +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
