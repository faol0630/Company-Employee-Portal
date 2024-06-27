package com.faol.security.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IllegalArgumentExceptionTest {

    @Test
    void illegalArgumentExceptionTest(){
        String message = "Message param";

        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException(message);
        });

    }

}