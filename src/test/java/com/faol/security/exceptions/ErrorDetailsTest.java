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
    }

    @Test
    void getError() {

        assertEquals("Error", error);
        assertInstanceOf(String.class, error);
        assertNotNull(error);
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
    }

    @Test
    void setErrorTest() {
        assertNotNull(error);
        assertEquals("Error", error);
        assertInstanceOf(String.class, error);
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
}