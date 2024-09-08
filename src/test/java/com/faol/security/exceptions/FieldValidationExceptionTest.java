/*
package com.faol.security.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FieldValidationExceptionTest {

    @Test
    void fieldValidationExceptionTest(){

        String message = "field validation exception test";

        Assertions.assertAll(
                */
/*() -> assertThrows(FieldValidationException.class, () -> {
                    throw new FieldValidationException(message);
                }),*//*

                () -> assertThrows(java.lang.RuntimeException.class, () -> {
                    throw new FieldValidationException(message);
                })
        );
    }
}
*/
