package com.algaworks.junit.blog.storage;

import com.algaworks.junit.blog.model.Post;

import java.util.List;
import java.util.Optional;

/*
 * Place where posts are stored
 */
public interface PostStorage {
    Post save(Post post);
    Optional<Post> findById(Long post);
    void remove(Long postId);
    List<Post> findAll();
}
