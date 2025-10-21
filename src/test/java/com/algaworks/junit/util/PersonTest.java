package com.algaworks.junit.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void whenNameIsSet_thenReturnName() {
        Person person = new Person("Eduardo", "Soares");
        Person person2 = new Person("Maria", "José");
        assertAll("Group name assertions",
                () -> assertEquals("Eduardo", person.getName()),
                () -> assertEquals("Soares", person.getLastname()),
                () -> assertEquals("Maria", person2.getName()),
                () -> assertEquals("José", person2.getLastname())
        );
    }
}