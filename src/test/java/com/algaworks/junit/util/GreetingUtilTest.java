package com.algaworks.junit.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreetingUtilTest {

    @Test
    void whenIntIsBetween5and11_thenReturnGoodMorning(){
        String greeting = GreetingUtil.greet(9);
        assertEquals("Good morning", greeting, "Wrong greeting");
    }

    @Test
    void whenIntIsBetween12and17_thenReturnGoodAfternoon(){
        String greeting = GreetingUtil.greet(13);
        assertEquals("Good afternoon", greeting, "Wrong greeting");
    }

    @Test
    void whenIntIsBetween18and23_thenReturnGoodEvening(){
        String greeting = GreetingUtil.greet(2);
        assertEquals("Good evening", greeting, "Wrong greeting");
    }



    @Test
    void throwsException(){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> GreetingUtil.greet(25));
        assertEquals("Invalid hour", illegalArgumentException.getMessage());
    }

    @Test
    void notThrowsException(){
        assertDoesNotThrow(() -> GreetingUtil.greet(9));
    }
}