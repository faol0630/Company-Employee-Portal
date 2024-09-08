package com.faol.security.exceptions;

/**
 * Clase de la Exception ResourceNotFoundException la cual recibe un String como argumento
 * Esta clase pasa a ser un atributo de la class Error details.
 * Pasa a ser el message de la clase Error details.
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

