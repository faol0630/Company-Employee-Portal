package com.faol.security.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenJwtConfigTest {

    @Test
    void tokenJWTConfigClassTest(){
        TokenJwtConfig tokenJwtConfig = new TokenJwtConfig();
        assertNotNull(tokenJwtConfig);
    }

}