package com.algaworks.junit.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MultiplicadorTest {

    @ParameterizedTest
    @EnumSource(value = Multiplicador.class, names = {"DOBRO", "TRIPLO"})
    void testTwoEnums(Multiplicador multiplicador){
        Assertions.assertNotNull(multiplicador.aplicarMultiplicador(10.0));
    }

    @ParameterizedTest
    @EnumSource(value = Multiplicador.class)
    void testAllEnums(Multiplicador multiplicador){
        Assertions.assertNotNull(multiplicador.aplicarMultiplicador(10.0));
    }

}