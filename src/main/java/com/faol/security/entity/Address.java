package com.faol.security.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


/**
 * Entity Address con atributos, getters, setters, toString sobreescrito, constructor vacio
 * constructor con argumentos y builder
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int address_id;

    @NotNull(message = "street cannot be null")
    private String street;

    @NotNull(message = "number cannot be null")
    private String number;

    @NotNull(message = "zipcode cannot be null")
    private String zipcode;

    /*@Override
    public String toString() {
        return "Address{" +
                "address_id=" + address_id +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }*/
}
