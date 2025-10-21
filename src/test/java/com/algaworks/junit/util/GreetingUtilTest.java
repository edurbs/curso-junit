package com.algaworks.junit.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
    void givenWrongHour_whenGreet_thenThrowIllegalArgumentException(){
        int wrongHour = 25;
        Executable greet = () -> GreetingUtil.greet(wrongHour);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, greet);

        assertEquals("Invalid hour", exception.getMessage());
    }

    @Test
    void givenValidHour_whenGreet_thenDoesNotThrowException(){
        int validHour = 9;
        Executable greet = () -> GreetingUtil.greet(validHour);
        assertDoesNotThrow(greet);
    }
}