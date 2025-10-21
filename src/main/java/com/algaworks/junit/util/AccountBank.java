package com.algaworks.junit.util;

import java.math.BigDecimal;


public class AccountBank {
    private BigDecimal balance;

    public AccountBank(BigDecimal balance) {
        if (balance == null) {
            throw new IllegalArgumentException("Balance cannot be null");
        }
        this.balance = balance;
    }

    public void withdraw(BigDecimal value) {
        if(value==null || value.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new IllegalArgumentException("Value cannot be null, zero or negative");
        }
        BigDecimal newBalance = balance.subtract(value);
        if(newBalance.compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Insufficient balance");
        }
        balance = balance.subtract(value);
    }

    public void deposit(BigDecimal value) {
        if(value==null || value.compareTo(BigDecimal.ZERO)<= 0){
            throw  new IllegalArgumentException("Value cannot be null, zero or negative");
        }
        balance = balance.add(value);
    }

    public BigDecimal balance() {
        return balance;
    }

}
