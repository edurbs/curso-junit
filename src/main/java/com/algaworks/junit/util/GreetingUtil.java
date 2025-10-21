package com.algaworks.junit.util;

public class GreetingUtil {

    private GreetingUtil() {
        
    }

    public static String greet(int hour) {
        if (hour >= 5 && hour <= 11) {
            return "Good morning";
        } else if(hour >= 12 && hour <= 17) {
            return "Good afternoon";
        } else if(hour >= 18 && hour <= 23 || hour>=0 && hour <= 4 ) {
            return "Good evening";
        }
        throw new IllegalArgumentException("Invalid hour");
    }

}
