package com.faol.security.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Clase que contiene los diferentes metodos que manejan las excepciones
 */
@RestControllerAdvice
public class HandlerExceptionController  {

    /**
     * Metodo que gestiona la excepcion que se lanza cuando no se encuentra un recurso como
     * un Entity o un parametro
     * @param resourceNotFoundException es la excepcion que se lanza cuando no se encuentra el recurso
     * @return una excepcion del tipo ResourceNotFoundException la cual contiene un estatus NotFound
     * y un objeto del tipo ErrorDetails con la fecha, mensaje de la excepcion y mensaje
     * personalizado del error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public static ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error("error searching for resource")
                .message(resourceNotFoundException.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    /**
     * Metodo que gestiona la excepcion IllegalArgumentException
     * @param illegalArgumentException es la excepcion que se lanza cuando el argumento que se pasa es invalido
     * @return una excepcion del tipo IllegalArgumentException la cual contiene un estatus BadRequest
     * y un objeto del tipo ErrorDetails con la fecha, mensaje de la excepcion y mensaje
     * personalizado del error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public static ResponseEntity<ErrorDetails> illegalArgumentException(IllegalArgumentException illegalArgumentException){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error("error validating the field")
                .message(illegalArgumentException.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Metodo que gestiona la excepcion EntityNotFoundException
     * @param entityNotFoundException es la excepcion que se lanza cuando no se encuentra el Entity buscado
     * @return una excepcion del tipo EntityNotFoundException la cual contiene un estatus NotFound
     * y un objeto del tipo ErrorDetails con la fecha, mensaje de la excepcion y mensaje
     * personalizado del error
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public static ResponseEntity<ErrorDetails> entityNotFoundException(EntityNotFoundException entityNotFoundException){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error("Entity noy found")
                .message(entityNotFoundException.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Metodo que gestiona la excepcion DataIntegrityViolationException
     * @param dataIntegrityViolationException es la excepcion que se lanza cuando algun atributo del entity es invalido
     * @return una excepcion del tipo DataIntegrityViolationException la cual contiene un estatus NotFound
     * y un objeto del tipo ErrorDetails con la fecha, mensaje de la excepcion y mensaje
     * personalizado del error
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public static ResponseEntity<ErrorDetails> dataIntegrityViolationException(com.faol.security.exceptions.DataIntegrityViolationException dataIntegrityViolationException){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error("Error while updating or creating entity")
                .message(dataIntegrityViolationException.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}
