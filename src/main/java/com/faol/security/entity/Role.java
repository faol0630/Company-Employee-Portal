package com.faol.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name") //para que no se repitan los roles
})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;

    @NotBlank(message = "name must not be blank")
    @Column(unique = true)
    private String name;


}
