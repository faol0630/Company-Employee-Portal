package com.faol.security.exceptions;


/**
 * Clase de la Exception DataIntegrityViolationException la cual recibe un String como argumento
 * Esta clase pasa a ser un atributo de la class Error details.
 * Pasa a ser el message de la clase Error details.
 */
public class DataIntegrityViolationException extends org.springframework.dao.DataIntegrityViolationException {

    public DataIntegrityViolationException(String msg) {
        super(msg);
    }
}
