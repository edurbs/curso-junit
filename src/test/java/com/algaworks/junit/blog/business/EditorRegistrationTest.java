package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.model.Editor;
import com.algaworks.junit.blog.storage.EditorStorage;
import com.algaworks.junit.custom.HumanPhraseDisplayNameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class EditorRegistrationTest {

    @Mock
    EditorStorage editorStorage;

    @Mock
    EmailSendingManager emailSendingManager;

    @InjectMocks
    EditorRegistration sut;

    Editor editor;

    @BeforeEach
    void init() {
        editor = new Editor(null, "Nome", "asd@asd.com", BigDecimal.TEN, true);
        when(editorStorage.save(any(Editor.class)))
                //.thenReturn(new Editor(76L, "Nome", "asd@asd.com", BigDecimal.TEN, true));
                .thenAnswer( invocation -> {
                    Editor editorReturned = invocation.getArgument(0, Editor.class);
                    editorReturned.setId(76L);
                    return editorReturned;
                });

    }

    @Test
    void givenValidEditor_whenCreate_returnId() {
        Editor savedEditor = sut.create(editor);
        assertEquals(76L, savedEditor.getId());
    }

    @Test
    void givenValidEditor_whenCreate_thenCallEditorStorageSave(){
        sut.create(editor);
        verify(editorStorage).save(editor);
    }

    @Test
    void givenValidEditor_whenCreateThrowsException_thenDoNotSendEmail(){
        when(editorStorage.save(editor))
                .thenThrow(new RuntimeException());
        assertAll(
                () -> assertThrows(RuntimeException.class, () -> sut.create(editor)),
                () -> verify(emailSendingManager, never()).sendEmail(any())
        );
    }

    @Test
    void givenValidEditor_whenCreate_thenSendEmailToEditor(){
        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        Editor editorSaved = sut.create(editor);
        verify(emailSendingManager).sendEmail(messageArgumentCaptor.capture());
        Message message = messageArgumentCaptor.getValue();
        assertEquals(editorSaved.getEmail(), message.getRecipient());
    }

}