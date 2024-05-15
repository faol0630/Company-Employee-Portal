package com.faol.security.exceptions;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {


    private String message;
    private String error;
    private Date date;

}
