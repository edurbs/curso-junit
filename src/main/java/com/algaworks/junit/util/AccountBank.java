package com.algaworks.junit.util;

import java.math.BigDecimal;


public class AccountBank {
    public static final String VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE
            = "Value cannot be null, zero or negative";
    public static final String VALUE_CANNOT_BE_NULL_OR_NEGATIVE
            = "Value cannot be null or negative";
    private BigDecimal balance;

    public AccountBank(BigDecimal balance) {
        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(VALUE_CANNOT_BE_NULL_OR_NEGATIVE);
        }
        this.balance = balance;
    }

    public void withdraw(BigDecimal value) {
        if(value==null || value.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new IllegalArgumentException(VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE);
        }
        BigDecimal newBalance = balance.subtract(value);
        if(newBalance.compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Insufficient balance");
        }
        balance = balance.subtract(value);
    }

    public void deposit(BigDecimal value) {
        if(value==null || value.compareTo(BigDecimal.ZERO)<= 0){
            throw  new IllegalArgumentException(VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE);
        }
        balance = balance.add(value);
    }

    public BigDecimal balance() {
        return balance;
    }

}
