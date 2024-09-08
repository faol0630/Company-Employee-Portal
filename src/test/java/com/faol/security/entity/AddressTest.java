package com.faol.security.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    Address address = new Address();

    @Test
    void setAddress_id() {
        //given
        int id = 48392;
        //when
        address.setAddress_id(id);
        //then
        Assertions.assertAll("Address setters test",
                () -> assertNotNull(address.getAddress_id()),
                () -> assertEquals(id, address.getAddress_id()),
                () -> assertInstanceOf(Integer.class, address.getAddress_id()),
                () -> assertDoesNotThrow( () -> address.setAddress_id(id))
        );
    }

    @Test
    void setStreet() {
        String street = "Camino";
        address.setStreet(street);
        Assertions.assertAll("Address setters test",
                () -> assertNotNull(address.getStreet()),
                () -> assertEquals(street, address.getStreet()),
                () -> assertInstanceOf(String.class, address.getStreet()),
                () -> assertDoesNotThrow( () -> address.setStreet(street))
        );
    }

    @Test
    void setNumber() {
        String number = "7";
        address.setNumber(number);
        Assertions.assertAll("Address setters test",
                () -> assertNotNull(address.getNumber()),
                () -> assertEquals(number, address.getNumber()),
                () -> assertInstanceOf(String.class, address.getNumber()),
                () -> assertDoesNotThrow( () -> address.setNumber(number))
        );
    }

    @Test
    void setZipcode() {
        String zipcode = "57482";
        address.setZipcode(zipcode);
        Assertions.assertAll("Address setters test",
                () -> assertNotNull(address.getZipcode()),
                () -> assertEquals(zipcode, address.getZipcode()),
                () -> assertInstanceOf(String.class, address.getZipcode()),
                () -> assertDoesNotThrow( () -> address.setZipcode(zipcode))
        );
    }

    @Test
    void addressToStringTest(){

        Address address1 = Address.builder()
                .address_id(3355441)
                .number("3")
                .street("London av")
                .zipcode("35443")
                .build();

        String result = address1.toString();
        String expected = "Address{" +
                "address_id=" + address1.getAddress_id() +
                ", street='" + address1.getStreet() + '\'' +
                ", number='" + address1.getNumber() + '\'' +
                ", zipcode='" + address1.getZipcode() + '\'' +
                '}';

        assertEquals(expected, result);
    }

    @Test
    void addressBuilderToStringTest(){

        Address.AddressBuilder builder = Address.builder()
                .address_id(342)
                .number("3")
                .street("Galos")
                .zipcode("34231");

        String addressToString = builder.toString();

        Assertions.assertAll(
                () -> assertNotNull(addressToString),
                () -> assertDoesNotThrow(builder::toString),
                () -> assertInstanceOf(Address.AddressBuilder.class, builder)

        );
    }

}