package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.model.Editor;
import com.algaworks.junit.blog.storage.EditorStorage;
import com.algaworks.junit.custom.HumanPhraseDisplayNameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
class EditorRegistrationTest {

    @Mock
    EditorStorage editorStorage;

    @Mock
    EmailSendingManager emailSendingManager;

    @InjectMocks
    EditorRegistration sut;

    Editor editor;

    AutoCloseable closeable;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
        editor = new Editor(null, "Nome", "asd@asd.com", BigDecimal.TEN, true);
        when(editorStorage.save(editor))
                .thenReturn(new Editor(76L, "Nome", "asd@asd.com", BigDecimal.TEN, true));

    }

    @Test
    void givenValidEditor_whenCreate_returnId() {
        Editor savedEditor = sut.create(editor);
        assertEquals(76L, savedEditor.getId());
    }

}