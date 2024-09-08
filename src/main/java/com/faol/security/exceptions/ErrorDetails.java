package com.faol.security.exceptions;

import lombok.*;

import java.util.Date;

/**
 * Clase que se usa para dar informacion mas detallada de la excepcion que se ha lanzado.
 * Contiene 3 atrobitos; un mensaje personalizado, un mensaje que lanza la excepcion
 * y la fecha con hora incluida del momento en que el error ocurrió.
 * Tambien contiene Getters , Setters, constructor vacio , constructor con todos los argumentos y
 * Builder
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {

    private String message;
    private String error;
    private Date date;

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", date=" + date +
                '}';
    }
}
