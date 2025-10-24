package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.exception.EditorNotFoundException;
import com.algaworks.junit.blog.exception.BusinessRuleException;
import com.algaworks.junit.blog.storage.EditorStorage;
import com.algaworks.junit.blog.model.Editor;

import java.util.Objects;

public class EditorRegistration {

    private final EditorStorage editorStorage;
    private final EmailSendingManager emailSendingManager;

    public EditorRegistration(EditorStorage editorStorage, EmailSendingManager emailSendingManager) {
        this.editorStorage = editorStorage;
        this.emailSendingManager = emailSendingManager;
    }

    public Editor create(Editor editor) {
        Objects.requireNonNull(editor);

        checkIfThereIsAnEditorUsingTheSameEmail(editor);
        editor = editorStorage.save(editor);
        sendRegistrationEmail(editor);

        return editor;
    }

    public Editor edit(Editor updatedEditor) {
        Objects.requireNonNull(updatedEditor);

        checkIfThereIsAnEditorUsingTheSameEmailWithDifferentId(updatedEditor);
        Editor editor = findEditor(updatedEditor);
        editor.updateWith(updatedEditor);

        return editorStorage.save(editor);
    }

    public void remove(Long editorId) {
        Objects.requireNonNull(editorId);
        editorStorage.findById(editorId).orElseThrow(EditorNotFoundException::new);
        editorStorage.remove(editorId);
    }

    private void checkIfThereIsAnEditorUsingTheSameEmail(Editor editor) {
        if(editorStorage.findByEmail(editor.getEmail()).isPresent()) {
            throw new BusinessRuleException("There is already an editor with that email " + editor.getEmail());
        }
    }

    private Editor findEditor(Editor updatedEditor) {
        return editorStorage.findById(updatedEditor.getId())
                .orElseThrow(EditorNotFoundException::new);
    }

    private void checkIfThereIsAnEditorUsingTheSameEmailWithDifferentId(Editor updatedEditor) {
        if(editorStorage.findByEmailWithDifferentIdThan(
                updatedEditor.getEmail(),
                updatedEditor.getId()).isPresent()) {
            throw new BusinessRuleException("There is already an editor with that email " + updatedEditor.getEmail());
        }
    }

    private void sendRegistrationEmail(Editor editor) {
        Message message = new Message(editor.getEmail(), "New registration", "Your registration has been completed");
        emailSendingManager.sendEmail(message);
    }
}
