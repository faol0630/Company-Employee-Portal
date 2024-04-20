package com.faol.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotNull(message = "age must not be null")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "invalid age format")
    private Integer age;

}
