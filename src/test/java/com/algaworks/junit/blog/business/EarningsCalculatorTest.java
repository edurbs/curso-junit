package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.model.Earnings;
import com.algaworks.junit.blog.model.Editor;
import com.algaworks.junit.blog.model.Post;
import com.algaworks.junit.blog.utility.SimpleTextProcessor;
import com.algaworks.junit.custom.HumanPhraseDisplayNameGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
class EarningsCalculatorTest {

    static EarningsCalculator sut;
    Editor editor;
    Post post;

    @BeforeAll
    static void beforeAll(){
        SimpleTextProcessor simpleTextProcessor = new SimpleTextProcessor();
        sut = new EarningsCalculator(simpleTextProcessor, BigDecimal.TEN);
    }

    @BeforeEach
    void beforeEach(){
        editor = new Editor(1L, "Eduardo", "email@provider.com", new BigDecimal(5), true);
        post = new Post(1L, "Nome da Publicação", "O texto da publicação é muito ruim", editor, "java-abc-123", null, false, false);
    }

    @Test
    void giveEditorPremium_whenCalculate_shouldBeCorrect(){
        Earnings earnings = sut.calculate(post);

        assertEquals(new BigDecimal("45"), earnings.getTotalEarnings());
        assertEquals(7, earnings.getWordCount());
        assertEquals(editor.getValuePaidPerWord(), earnings.getValuePaidPerWord());
    }

    @Test
    void giveEditorNotPremium_whenCalculate_shouldBeCorrect(){
        editor.setPremium(false);

        Earnings earnings = sut.calculate(post);

        assertEquals(new BigDecimal("35"), earnings.getTotalEarnings());
        assertEquals(7, earnings.getWordCount());
        assertEquals(editor.getValuePaidPerWord(), earnings.getValuePaidPerWord());
    }

}