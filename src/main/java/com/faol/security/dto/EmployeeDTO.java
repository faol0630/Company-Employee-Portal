package com.faol.security.dto;

import lombok.*;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDTO {


    private Long id_employee;

    private String name;

    private String lastname;

    private String email;

    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDTO that)) return false;
        return Objects.equals(
                getId_employee(),
                that.getId_employee()) && Objects.equals(getName(),
                that.getName()) && Objects.equals(getLastname(),
                that.getLastname()) && Objects.equals(getEmail(),
                that.getEmail()) && Objects.equals(getUsername(),
                that.getUsername()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_employee(), getName(), getLastname(), getEmail(), getUsername());
    }
}
