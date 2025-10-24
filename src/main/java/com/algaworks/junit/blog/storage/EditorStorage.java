package com.algaworks.junit.blog.storage;

import com.algaworks.junit.blog.model.Editor;

import java.util.List;
import java.util.Optional;
/*
 * Place where editors are stored
 */
public interface EditorStorage {
    Editor save(Editor editor);
    Optional<Editor> findById(Long editor);
    Optional<Editor> findByEmail(String email);
    Optional<Editor> findByEmailWithDifferentIdThan(String email, Long id);
    void remove(Long editorId);
    List<Editor> findAll();
}
