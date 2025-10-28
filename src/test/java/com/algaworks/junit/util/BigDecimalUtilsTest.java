package com.algaworks.junit.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BigDecimalUtilsTest {

    @ParameterizedTest
    @CsvSource({
        "10.00,10",
        "9,9.00"
    })
    void equals(BigDecimal x, BigDecimal y){
        assertTrue(BigDecimalUtils.equals(x, y));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/numeros.csv")
    void notEqualsFile(BigDecimal x, BigDecimal y){
        assertFalse(BigDecimalUtils.equals(x, y));
    }

}