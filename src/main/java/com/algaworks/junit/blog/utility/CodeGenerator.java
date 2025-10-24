package com.algaworks.junit.blog.utility;

public class CodeGenerator {

    private CodeGenerator() {

    }

    public static String generate() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
