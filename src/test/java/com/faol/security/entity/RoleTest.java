package com.faol.security.entity;

import com.faol.security.dto.CompanyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void RoleBuilderTest(){
        Role role = Role.builder()
                .id_role(453L)
                .name("USER")
                .build();

        Assertions.assertAll( "Role builder test",
                () -> assertEquals(453L, role.getId_role()),
                () -> assertEquals("USER", role.getName()),
                () -> assertNotNull(role)

        );
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

    @Test
    void roleToString(){
        Role role = Role.builder()
                .id_role(1L)
                .name("USER")
                .build();

        String result = role.toString();
        String expected = "Role{" +
                "id_role=" + role.getId_role() +
                ", name='" + role.getName() + '\'' +
                '}';

        assertEquals(expected, result);
    }

    @Test
    void hashCodeConsistencyTest(){

        Role role = Role.builder()
                .id_role(1L)
                .name("ADMIN")
                .build();

        int initialHashCode = role.hashCode();
        int subSequentHashCode = role.hashCode();

        assertEquals(initialHashCode, subSequentHashCode, "Hash code should be consistent");

    }

    @Test
    void roleBuilderToStringTest(){

        Role.RoleBuilder builder = Role.builder()
                .id_role(1L)
                .name("USER");

        String builderToString = builder.toString();

        Assertions.assertAll(
                () -> assertDoesNotThrow( () -> builder.name("USER")),
                () -> assertInstanceOf( Role.RoleBuilder.class, builder),
                () -> assertNotNull(builderToString)
        );
    }

}