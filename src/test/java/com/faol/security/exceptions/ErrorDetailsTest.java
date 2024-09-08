package com.faol.security.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDetailsTest {

    ErrorDetails errorDetails;
    String message;
    String error;
    Date date;

    @BeforeEach
    void init(){
        errorDetails = new ErrorDetails();

        errorDetails.setMessage("Mensaje");
        message = errorDetails.getMessage();

        errorDetails.setError("Error");
        error = errorDetails.getError();

        errorDetails.setDate(new Date());
        date = errorDetails.getDate();
    }

    @Test
    void getMessage() {

        assertEquals("Mensaje", message);
        assertInstanceOf(String.class, message);
        assertNotNull(message);
        assertDoesNotThrow( () -> errorDetails.getMessage());
    }

    @Test
    void getError() {

        assertEquals("Error", error);
        assertInstanceOf(String.class, error);
        assertNotNull(error);
        assertDoesNotThrow( () -> errorDetails.getError());
    }

    @Test
    void getDate() {
        assertNotNull(date);
        assertInstanceOf(java.util.Date.class, date);
    }

    @Test
    void setMessage() {

        assertNotNull(message);
        assertEquals("Mensaje", message);
        assertInstanceOf(String.class, message);
        assertDoesNotThrow( () -> errorDetails.setMessage("Mensaje"));
    }

    @Test
    void setErrorTest() {
        assertNotNull(error);
        assertEquals("Error", error);
        assertInstanceOf(String.class, error);
        assertDoesNotThrow( () -> errorDetails.setError("Error"));
    }

    @Test
    void setDateTest() {
        assertNotNull(date);
        assertInstanceOf(java.util.Date.class, date);
    }

    @Test
    void builder() {

        ErrorDetails errorDetails1 = ErrorDetails.builder()
                .error("Error 1")
                .message("Message 1")
                .date(new Date())
                .build();

        assertNotNull(errorDetails1);
        assertInstanceOf(ErrorDetails.class, errorDetails1);

    }

    @Test
    void testToString() {

        String result = errorDetails.toString();
        String expected = "ErrorDetails{" +
                "message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", date=" + date +
                '}';

        assertEquals(expected, result);
    }


    @Test
    void errorDetailsBuilderToStringTest() {

        ErrorDetails.ErrorDetailsBuilder builder = ErrorDetails.builder()
                .error("Error")
                .date(new Date())
                .message("something unexpected has happened");

        String errorDetailsToString = builder.toString();

        Assertions.assertAll(
                () -> assertDoesNotThrow( () -> builder.error("Error")),
                () -> assertInstanceOf(ErrorDetails.ErrorDetailsBuilder.class, builder),
                () -> assertNotNull(errorDetailsToString)
        );
    }
}