package com.faol.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController  {

    @ExceptionHandler({RuntimeException.class})  //llaves si son varias excepciones separadas por coma
    public ResponseEntity<ErrorDetails> errorHandling(RuntimeException ex){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error(ex.getLocalizedMessage())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public static ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error("error searching for resource")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(FieldValidationException.class)
    public static ResponseEntity<ErrorDetails> handleFieldValidationException(FieldValidationException ex){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error("error validating the field")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public static ResponseEntity<ErrorDetails> illegalArgumentException(IllegalArgumentException ex){

        ErrorDetails error = ErrorDetails.builder()
                .date(new Date())
                .error("error validating the field")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //estas exceptiones requieren configuracion adicional en @Service(if else) y @Controller(try catch).

}
