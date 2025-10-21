package com.algaworks.junit.util;

import java.util.ArrayList;
import java.util.List;

public class NumberFilter {

    private NumberFilter() {

    }

    public static List<Integer> evenNumbers(List<Integer> numbers) {
        return new ArrayList<>(numbers).stream().filter(number -> number % 2 == 0).toList();
    }

    public static List<Integer> oddNumbers(List<Integer> numbers) {
        return new ArrayList<>(numbers).stream().filter(number -> number % 2 != 0).toList();
    }

    public static boolean isPositive(int number) {
        return number >= 0;
    }

}
