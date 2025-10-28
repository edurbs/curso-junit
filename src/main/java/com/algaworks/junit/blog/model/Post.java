package com.algaworks.junit.blog.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Builder
@Data
public class Post {
    private Long id;
    private String title;
    private String content;
    private Editor author;
    private String slug;
    private Earnings earnings;
    private boolean paid;
    private boolean published;

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

}
