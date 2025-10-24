package com.algaworks.junit.blog.model;

import java.time.OffsetDateTime;

public class Notification {
    private OffsetDateTime offsetDateTime;
    private String content;

    public Notification(OffsetDateTime offsetDateTime, String content) {
        this.offsetDateTime = offsetDateTime;
        this.content = content;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
