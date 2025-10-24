package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.model.Earnings;
import com.algaworks.junit.blog.model.Editor;
import com.algaworks.junit.blog.model.Post;

import java.math.BigDecimal;
import java.util.Objects;

public class EarningsCalculator {

    private final TextProcessor textProcessor;
    private final BigDecimal premiumBonus;

    public EarningsCalculator(TextProcessor textProcessor,
                              BigDecimal premiumBonus) {
        Objects.requireNonNull(textProcessor);
        Objects.requireNonNull(premiumBonus);
        this.textProcessor = textProcessor;
        this.premiumBonus = premiumBonus;
    }

    public Earnings calculate(Post post) {
        Objects.requireNonNull(post);
        Editor author = post.getAuthor();
        Objects.requireNonNull(author);

        BigDecimal valuePaidPerWord = author.getValuePaidPerWord();
        int wordCount = textProcessor.wordCount(post.getContent());
        BigDecimal totalEarnings = valuePaidPerWord.multiply(BigDecimal.valueOf(wordCount));

        if (post.getAuthor().isPremium()) {
            totalEarnings = totalEarnings.add(premiumBonus);
        }

        return new Earnings(valuePaidPerWord, wordCount, totalEarnings);
    }
}
