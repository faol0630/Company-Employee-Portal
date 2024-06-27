package com.faol.security;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContextException;

import static org.junit.jupiter.api.Assertions.*;

class SecurityApplicationTest {

    /*@Test
    void main() throws Exception {


        String expectedOutput = "";
        String[] args = {""};

        assertThrows( ApplicationContextException.class, () -> {
            SecurityApplication.main(args);
        });
    }*/

    @Test
    void mainEmpty() throws Exception {


        String expectedOutput = "";
        String[] args = {};

        assertThrows( ArrayIndexOutOfBoundsException.class, () -> {
            SecurityApplication.main(args);
        });
    }
}
