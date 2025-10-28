package com.algaworks.junit.blog.utility;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlugConverterTest {

    @Test
    void convert(){
        try(MockedStatic<CodeGenerator> mockedStatic = Mockito.mockStatic(CodeGenerator.class)){
            mockedStatic.when(CodeGenerator::generate).thenReturn("123456");
            String slug = SlugConverter.convertWithCode("Olá java");
            assertEquals("ola-java-123456", slug);

        }
    }

}