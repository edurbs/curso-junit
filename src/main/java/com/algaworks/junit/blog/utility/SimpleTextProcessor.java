package com.algaworks.junit.blog.utility;

import com.algaworks.junit.blog.business.TextProcessor;

public class SimpleTextProcessor implements TextProcessor {
    @Override
    public int wordCount(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        return text.split(" ").length;
    }
}
