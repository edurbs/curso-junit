package com.algaworks.junit.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static boolean equals(BigDecimal x, BigDecimal y) {
        return x.compareTo(y) == 0;
    }

}
