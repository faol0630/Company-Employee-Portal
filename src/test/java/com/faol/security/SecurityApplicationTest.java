package com.faol.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContextException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SecurityApplicationTest {

    @Test
    void mainEmpty() throws Exception {


        //String expectedOutput = "";
        String[] args = {};

        assertThrows( ArrayIndexOutOfBoundsException.class, () -> {
            SecurityApplication.main(args);
        });
    }
}
