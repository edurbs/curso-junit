package com.algaworks.junit.custom;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.List;

public class HumanPhraseDisplayNameGenerator implements DisplayNameGenerator {

    private int lastPosition;

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return formatCase(testClass.getSimpleName());
    }

    @Override
    public String generateDisplayNameForNestedClass(List<Class<?>> enclosingInstanceTypes, Class<?> nestedClass) {
        return nestedClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForMethod(List<Class<?>> enclosingInstanceTypes, Class<?> testClass, Method testMethod) {
        return formatCase(testMethod.getName());
    }

    private String formatCase(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        String phrase = convertToHumanPhrase(input);
        if (phrase.isEmpty()) {
            return "";
        }
        char firstLetter = Character.toUpperCase(phrase.charAt(0));
        String rest = "";
        if (phrase.length() > 1) {
            rest = phrase.substring(1).toLowerCase();
        }
        return firstLetter + rest;
    }

    private String convertToHumanPhrase(String input) {
        String output = input.replace("_", ", ");
        StringBuilder words = new StringBuilder();
        lastPosition = 0;
        for (int i = 1; i < output.length(); i++) {
            appendSpace(output, i, words);
        }
        String lastSegment = output.substring(lastPosition);
        words.append(lastSegment);
        return words.toString().trim();
    }

    private void appendSpace(String output, int actualPosition, StringBuilder words) {
        char currentChar = output.charAt(actualPosition);
        char previousChar = output.charAt(actualPosition - 1);

        if (isUpperCaseThatNotFollowsADigit(currentChar, previousChar) ||
                isDigitThatFollowsALetter(currentChar, previousChar) ||
                isLetterThatFollowsADigit(currentChar, previousChar)) {
            addWordAndSpace(output, actualPosition, words);
        }
    }

    private boolean isUpperCaseThatNotFollowsADigit(char currentChar, char previousChar) {
        return Character.isUpperCase(currentChar) && !Character.isDigit(previousChar);
    }

    private boolean isDigitThatFollowsALetter(char digit, char letter) {
        return Character.isDigit(digit) && Character.isLetter(letter);
    }

    private boolean isLetterThatFollowsADigit(char letter, char digit) {
        return Character.isLetter(letter) && Character.isDigit(digit);
    }

    private void addWordAndSpace(String output, int actualPosition, StringBuilder words) {
        if (actualPosition > lastPosition) {
            words.append(output, lastPosition, actualPosition);
            words.append(" ");
        }
        lastPosition = actualPosition;
    }

}
