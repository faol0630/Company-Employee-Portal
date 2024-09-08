package com.faol.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Entity Employee con atributos, getters, setters, toString sobreescrito, constructor vacio
 * constructor con argumentos y builder
 */
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

    @JsonBackReference //para evitar bucles infinitos
    @ManyToOne(targetEntity = Department.class)
    private Department department;

    @ManyToMany
    @JoinTable(name = "employees_roles",
            joinColumns = @JoinColumn(name = "id_employee"), //esta tabla
            inverseJoinColumns = @JoinColumn(name = "id_role"),
            uniqueConstraints = { @UniqueConstraint(columnNames = { "id_employee", "id_role"})}
    )
    private List<Role> roles;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_int_id")
    private Address address;

    @Override
    public String toString() {
        return "Employee{" +
                "id_employee=" + id_employee +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                //", department=" + department + quitar para evitar bucle infinito donde sea bidireccional
                ", roles=" + roles +
                ", address=" + address +
                '}';
    }
}
