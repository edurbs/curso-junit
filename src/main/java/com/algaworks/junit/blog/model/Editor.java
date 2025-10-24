package com.algaworks.junit.blog.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Editor {
    private Long id;
    private String name;
    private String email;
    private BigDecimal valuePaidPerWord;
    private boolean premium;
    private OffsetDateTime creationDate = OffsetDateTime.now();

    public Editor() {

    }

    public Editor(String name, String email, BigDecimal valuePaidPerWord, boolean premium) {
        this(null, name, email, valuePaidPerWord, premium);
    }

    public Editor(Long id, String name, String email, BigDecimal valuePaidPerWord, boolean premium) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        Objects.requireNonNull(valuePaidPerWord);
        this.id = id; // Can be null, in case of a new editor
        this.name = name;
        this.email = email;
        this.valuePaidPerWord = valuePaidPerWord;
        this.premium = premium;
    }

    /**
     * Updates only with allowed data
     * @param editor
     */
    public void updateWith(Editor editor) {
        Objects.requireNonNull(editor);
        this.name = editor.name;
        this.email = editor.email;
        this.valuePaidPerWord = editor.valuePaidPerWord;
        this.premium = editor.premium;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getValuePaidPerWord() {
        return valuePaidPerWord;
    }

    public void setValuePaidPerWord(BigDecimal valuePaidPerWord) {
        this.valuePaidPerWord = valuePaidPerWord;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Editor editor = (Editor) o;
        return Objects.equals(id, editor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
