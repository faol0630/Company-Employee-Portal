package com.faol.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

/**
 * Entity Role con atributos, getters, setters, toString sobreescrito, constructor vacio
 * constructor con argumentos y builder
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name") //para que no se repitan los roles
})
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;

    @NotBlank(message = "name must not be blank")
    @Column(unique = true)
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "id_role=" + id_role +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_role(), getName());
    }
}
