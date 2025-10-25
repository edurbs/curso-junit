package com.algaworks.junit.util;

import com.algaworks.junit.custom.HumanPhraseDisplayNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
class AccountBankTest {

    public static final String VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE
            = "Value cannot be null, zero or negative";
    public static final String VALUE_CANNOT_BE_NULL_OR_NEGATIVE
            = "Value cannot be null or negative";
    public static final String INSUFFICIENT_BALANCE
            = "Insufficient balance";

    AccountBank create(BigDecimal balance) {
        return new AccountBank(balance);
    }


    @Nested
    class GivenBalanceNegative {

        @Nested
        class WhenCreate {

            @Test
            void thenThrowException() {
                BigDecimal negativeBalance = BigDecimal.TEN.negate();
                Executable accountBank = () -> create(negativeBalance);
                IllegalArgumentException illegalArgumentException
                        = assertThrows(IllegalArgumentException.class, accountBank);
                assertEquals(VALUE_CANNOT_BE_NULL_OR_NEGATIVE, illegalArgumentException.getMessage());
            }

        }

    }

    @Nested
    class GivenBalanceZero {

        BigDecimal zeroBalance = BigDecimal.ZERO;

        AccountBank createWithBalanceZero() {
            return new AccountBank(zeroBalance);
        }

        @Nested
        class WhenCreate {

            @Test
            void thenDoesNotThrowException() {
                assertDoesNotThrow(GivenBalanceZero.this::createWithBalanceZero);
            }

        }

        @Nested
        class WhenGetBalance{

            @Test
            void thenBalanceIsZero(){
                var sut = createWithBalanceZero();
                assertEquals(BigDecimal.ZERO, sut.balance());
            }
        }

        @Nested
        class WhenWithdraw{

            @Test
            void thenThrowsException(){
                var sut = createWithBalanceZero();
                BigDecimal oneCent = new BigDecimal("0.01");
                RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> sut.withdraw(oneCent));
                assertEquals(INSUFFICIENT_BALANCE, runtimeException.getMessage());
            }
        }

        @Nested
        class WhenDeposit{

            @Test
            void thenIncreaseBalance(){
                var sut = createWithBalanceZero();
                sut.deposit(BigDecimal.ONE);
                assertEquals(BigDecimal.ONE, sut.balance());
            }
        }
    }


    @Nested
    class GivenBalanceTen {

        @Nested
        class WhenCreate {

            @Test
            void thenDoesNotThrowException() {
                assertDoesNotThrow(() -> create(BigDecimal.TEN));
            }

        }

        @Nested
        class WhenWithdrawOneHundred{

            @Test
            void thenThrowException(){
                var sut = create(BigDecimal.TEN);
                var oneHundred = BigDecimal.valueOf(100);
                Executable withdraw = () -> sut.withdraw(oneHundred);
                var exception = assertThrows(RuntimeException.class, withdraw);
                assertEquals(INSUFFICIENT_BALANCE, exception.getMessage());
            }
        }

    }

    @Nested
    class GivenNull{
        BigDecimal oneHundred = new BigDecimal("100");

        @Nested
        class WhenCreate{

            @Test
            void thenThrowsException(){
                Executable create = () -> create(null);
                var exception = assertThrows(IllegalArgumentException.class, create);
                assertEquals(VALUE_CANNOT_BE_NULL_OR_NEGATIVE, exception.getMessage());
            }
        }

        @Nested
        class WhenWithdraw{

            @Test
            void thenThrowsException(){
                AccountBank accountBank = create(oneHundred);
                Executable withdraw = () -> accountBank.withdraw(null);
                var exception = assertThrows(IllegalArgumentException.class, withdraw);
                assertEquals(VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE, exception.getMessage());
            }
        }

        @Nested
        class WhenDeposit{

            @Test
            void thenThrowsException(){
                AccountBank accountBank = create(oneHundred);
                Executable deposit = () -> accountBank.deposit(null);
                var exception = assertThrows(IllegalArgumentException.class, deposit);
                assertEquals(VALUE_CANNOT_BE_NULL_ZERO_OR_NEGATIVE, exception.getMessage());
            }

        }

    }

}
