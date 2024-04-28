package com.faol.security.dto;

import lombok.*;


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

}
