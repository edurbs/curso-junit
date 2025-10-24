package com.algaworks.junit.blog.model;

import java.util.Objects;

public class Post {
    private Long id;
    private String title;
    private String content;
    private Editor author;
    private String slug;
    private Earnings earnings;
    private boolean paid;
    private boolean published;

    public Post() {
    }

    public Post(String title, String content, Editor author, boolean paid, boolean published) {
        this(null, title, content, author, null, null, paid, published);
    }

    public Post(Long id, String title, String content, Editor author, String slug, Earnings earnings, boolean paid, boolean published) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(content);
        Objects.requireNonNull(author);
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.slug = slug;
        this.earnings = earnings;
        this.paid = paid;
        this.published = published;
    }

    public void publish() {
        this.published = true;
    }

    public void markAsPaid() {
        this.paid = true;
    }

    public void updateWithData(Post post) {
        Objects.requireNonNull(post);
        this.title = post.title;
        this.content = post.content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Editor getAuthor() {
        return author;
    }

    public void setAuthor(Editor author) {
        this.author = author;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Earnings getEarnings() {
        return earnings;
    }

    public void setEarnings(Earnings earnings) {
        this.earnings = earnings;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
