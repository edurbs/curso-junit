package com.algaworks.junit.blog.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Earnings {
    private BigDecimal valuePaidPerWord;
    private int wordCount;
    private BigDecimal totalEarnings;

    public Earnings(BigDecimal valuePaidPerWord, int wordCount, BigDecimal totalEarnings) {
        this.valuePaidPerWord = valuePaidPerWord;
        this.wordCount = wordCount;
        this.totalEarnings = totalEarnings;
    }

    public BigDecimal getValuePaidPerWord() {
        return valuePaidPerWord;
    }

    public void setValuePaidPerWord(BigDecimal valuePaidPerWord) {
        this.valuePaidPerWord = valuePaidPerWord;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earnings earnings = (Earnings) o;
        return wordCount == earnings.wordCount && Objects.equals(valuePaidPerWord, earnings.valuePaidPerWord) && Objects.equals(totalEarnings, earnings.totalEarnings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valuePaidPerWord, wordCount, totalEarnings);
    }
}
