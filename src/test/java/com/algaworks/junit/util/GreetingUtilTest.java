package com.algaworks.junit.util;

import com.algaworks.junit.custom.HumanPhraseDisplayNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
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

    @ParameterizedTest
    @ValueSource(ints = {5,6,7,8,9,10,11})
    void givenMorningHour_whenGreet_thenReturnGoodMorning(int hour){
        String greeting = GreetingUtil.greet(hour);
        assertEquals("Good morning", greeting, "Wrong greeting");
    }
}