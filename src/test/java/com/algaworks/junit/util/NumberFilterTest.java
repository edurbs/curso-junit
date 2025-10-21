package com.algaworks.junit.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberFilterTest {

    @Test
    void givenAllNumbers_whenFilterEvenNumber_thenReturnEvenNumbers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers = NumberFilter.evenNumbers(numbers);
        assertEquals(5, evenNumbers.size());
        assertEquals(List.of(2, 4, 6, 8, 10), evenNumbers);
    }
}