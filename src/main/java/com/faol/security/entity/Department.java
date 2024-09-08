package com.faol.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


/**
 * Entity Department con atributos, getters, setters, toString sobreescrito, constructor vacio
 * constructor con argumentos y builder
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int department_id;

    @NotNull(message = "department_name must not be null")
    private String deparment_name;

    //entity dueña de la relacion:
    @JsonManagedReference //para evitar bucles infinitos
    @OneToMany(targetEntity = Employee.class, fetch = FetchType.LAZY, mappedBy = "department")
    private List<Employee> employeeList;

    @JsonBackReference //para evitar bucles infinitos
    @ManyToOne(targetEntity = Company.class)
    private Company company;

    @Override
    public String toString() {
        return "Department{" +
                "department_id=" + department_id +
                ", deparment_name='" + deparment_name + '\'' +
                //", employeeList=" + employeeList + quitar para evitar bucle infinito donde sea bidireccional
                //", company=" + company + quitar para evitar bucle infinito donde sea bidireccional
                '}';
    }
}
