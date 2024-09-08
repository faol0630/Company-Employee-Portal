package com.faol.security.exceptions;

/**
 * Clase de la Exception EntityNotFoundException la cual recibe un String como argumento
 * Esta clase pasa a ser un atributo de la class Error details.
 * Pasa a ser el message de la clase Error details.
 */
public class EntityNotFoundException extends jakarta.persistence.EntityNotFoundException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
