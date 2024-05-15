package com.faol.security.exceptions;

public class FieldValidationException extends RuntimeException{

    public FieldValidationException(String message) {
        super(message);
    }
}
//esto pasa como parametro o como atributo de la class Error.
//pasa a ser el message de la class Error.
