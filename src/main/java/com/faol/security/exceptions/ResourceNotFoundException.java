package com.faol.security.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
//esto pasa como parametro o como atributo de la class Error.
//pasa a ser el message de la class Error.
