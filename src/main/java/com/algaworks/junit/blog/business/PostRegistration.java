package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.exception.BusinessRuleException;
import com.algaworks.junit.blog.exception.PostNotFoundException;
import com.algaworks.junit.blog.model.Notification;
import com.algaworks.junit.blog.model.Post;
import com.algaworks.junit.blog.storage.PostStorage;
import com.algaworks.junit.blog.utility.SlugConverter;

import java.time.OffsetDateTime;
import java.util.Objects;

public class PostRegistration {

    private final PostStorage postStorage;
    private final EarningsCalculator earningsCalculator;
    private final NotificationManager notificationManager;

    public PostRegistration(PostStorage postStorage,
                           EarningsCalculator earningsCalculator,
                           NotificationManager notificationManager) {
        this.postStorage = postStorage;
        this.earningsCalculator = earningsCalculator;
        this.notificationManager = notificationManager;
    }

    public Post create(Post post) {
        Objects.requireNonNull(post);
        post.setSlug(createSlug(post));
        post.setEarnings(this.earningsCalculator.calculate(post));
        post = postStorage.save(post);
        sendNotification(post);
        return post;
    }

    public Post edit(Post updatedPost) {
        Objects.requireNonNull(updatedPost);

        Post post = this.postStorage.findById(updatedPost.getId())
                .orElseThrow(PostNotFoundException::new);
        post.updateWithData(updatedPost);

        if (!post.isPaid()) {
            post.setEarnings(this.earningsCalculator.calculate(post));
        }

        return postStorage.save(post);
    }

    public void remove(Long postId) {
        Objects.requireNonNull(postId);
        Post post = this.postStorage.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (post.isPublished()) {
            throw new BusinessRuleException("A published post cannot be removed");
        }
        if (post.isPaid()) {
            throw new BusinessRuleException("A paid post cannot be removed");
        }
        this.postStorage.remove(postId);
    }

    private String createSlug(Post post) {
        return SlugConverter.convertWithCode(post.getTitle());
    }

    private void sendNotification(Post post) {
        Notification notification = new Notification(
                OffsetDateTime.now(),
                "New post created -> " + post.getTitle()
        );
        this.notificationManager.send(notification);
    }
}
