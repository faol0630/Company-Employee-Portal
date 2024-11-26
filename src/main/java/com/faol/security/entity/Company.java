package com.faol.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Entity Company con atributos, getters, setters, toString sobreescrito, constructor vacio
 * constructor con argumentos y builder
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int company_id;

    @NotNull(message = "company name cannot be null")
    private String company_name;

    @NotNull(message = "country cannot be null")
    private String country;

    //entity dueña de la relacion:
    //@JsonManagedReference //para evitar bucles infinitos
    @OneToMany(targetEntity = Department.class, fetch = FetchType.LAZY, mappedBy = "company")
    private List<Department> departmentList;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_int_id")
    private Address address;

    /*@Override
    public String toString() {
        return "Company{" +
                "company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", country='" + country + '\'' +
                //", departmentList=" + departmentList + quitar para evitar bucle infinito donde sea bidireccional
                ", address=" + address +
                '}';
    }*/
}
