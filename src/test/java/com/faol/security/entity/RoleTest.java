package com.faol.security.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    Role role;

    @BeforeEach
    void init(){
        role = new Role();
        role.setId_role(5132L);
        role.setName("Pedro");
    }

    @Test
    void getId_role() {
        //given
        //when
        Long result = role.getId_role();
        //then
        assertNotNull(result);
        assertInstanceOf(Long.class, result);
        assertEquals(5132L ,result);
    }

    @Test
    void getName() {
        //given
        //when
        String name = role.getName();
        //then
        assertEquals("Pedro", name);
        assertInstanceOf(String.class, name);
        assertNotNull(name);
    }
}