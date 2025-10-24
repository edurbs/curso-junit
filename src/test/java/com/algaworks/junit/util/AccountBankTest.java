package com.algaworks.junit.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountBankTest {

    public static final String VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE = "Value cannot be null, zero or negative";

    @Nested
    class Create{
        @Test
        void givenBalanceNull_whenCreate_thenThrowException() {
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> getAccountBank(null));
            assertEquals( "Balance cannot be null", illegalArgumentException.getMessage());
        }

        @Test
        void givenBalanceNotNull_whenCreate_thenDoesNotThrowsException() {
            assertDoesNotThrow(() -> getAccountBank(BigDecimal.ONE.negate()));
            assertDoesNotThrow(() -> getAccountBank(BigDecimal.ZERO));
            assertDoesNotThrow(() -> getAccountBank(BigDecimal.ONE));
        }

    }

    @Nested
    class Balance{

        @Test
        void givenBalance_whenCreate_thenSetBalance(){
            AccountBank accountBank = getAccountBank();
            assertEquals(BigDecimal.ZERO, accountBank.balance());
        }

        @Test
        void givenAccount_whenGetBalance_thenReturnBalnace(){
            AccountBank accountBank = getAccountBank(BigDecimal.TEN);
            assertEquals(BigDecimal.TEN, accountBank.balance());
        }


    }

    @Nested
    class Withdraw{
        @Test
        void givenValueNull_whenWithdraw_thenThrowException() {
            AccountBank accountBank = getAccountBank();
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> accountBank.withdraw(null));
            assertEquals(VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE, illegalArgumentException.getMessage());
        }

        @Test
        void givenValueZero_whenWithDraw_thenThrowException(){
            AccountBank accountBank = getAccountBank();
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> accountBank.withdraw(BigDecimal.ZERO));
            assertEquals(VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE, illegalArgumentException.getMessage());
        }
        @Test
        void givenValueNegative_whenWithDraw_thenThrowException(){
            AccountBank accountBank = getAccountBank();
            BigDecimal negative = BigDecimal.ONE.negate();
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> accountBank.withdraw(negative));
            assertEquals(VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE, illegalArgumentException.getMessage());
        }

        @Test
        void givenValue_whenWithdraw_thenSubtractFromBalance(){
            AccountBank accountBank = getAccountBank(BigDecimal.TEN);
            accountBank.withdraw(BigDecimal.TWO);
            BigDecimal balance = accountBank.balance();
            assertEquals(BigDecimal.valueOf(8L), balance);
        }

        @Test
        void givenValue_whenWithdrawWithoutBalance_thenThrowException(){
            AccountBank accountBank = getAccountBank(BigDecimal.TWO);
            RuntimeException exception = assertThrows(RuntimeException.class, () -> accountBank.withdraw(BigDecimal.TEN));
            assertEquals("Insufficient balance", exception.getMessage());
        }
    }

    @Nested
    class Deposit{

        @Test
        void givenInvalidValue_whenDeposit_thenThrowException(){
            BigDecimal negative = BigDecimal.ZERO.negate();
            AccountBank accountBank = getAccountBank();
            assertThrows(IllegalArgumentException.class, () -> accountBank.deposit(null));
            assertThrows(IllegalArgumentException.class, () -> accountBank.deposit(BigDecimal.ZERO));
            assertThrows(IllegalArgumentException.class, () -> accountBank.deposit(negative));
        }

        @Test
        void givenValidValue_whenDeposit_thenAddBalance(){
            AccountBank accountBank = getAccountBank(BigDecimal.ONE);
            accountBank.deposit(BigDecimal.ONE);
            assertEquals(BigDecimal.TWO, accountBank.balance());
        }

    }

    private AccountBank getAccountBank() {
        return new AccountBank(BigDecimal.ZERO);
    }
    private AccountBank getAccountBank(BigDecimal balance) {
        return new AccountBank(balance);
    }


}