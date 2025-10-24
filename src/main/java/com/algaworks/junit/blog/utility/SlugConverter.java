package com.algaworks.junit.blog.utility;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugConverter {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private SlugConverter() {
    }

    public static String convertWithCode(String text) {
        return convert(text) + "-" + CodeGenerator.generate();
    }

    public static String convert(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String is null");
        }

        if (text.isEmpty()) {
            throw new IllegalArgumentException("String is empty");
        }

        String nowhitespace = WHITESPACE.matcher(text).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");

        return slug.toLowerCase(Locale.ENGLISH);
    }
}
