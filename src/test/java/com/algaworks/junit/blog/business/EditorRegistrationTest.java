package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.exception.BusinessRuleException;
import com.algaworks.junit.blog.model.Editor;
import com.algaworks.junit.blog.storage.EditorStorage;
import com.algaworks.junit.custom.HumanPhraseDisplayNameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class EditorRegistrationTest {

    @Spy
    Editor editor = new Editor(null, "Nome", "asd@asd.com", BigDecimal.TEN, true);

    @Mock
    EditorStorage editorStorage;

    @Mock
    EmailSendingManager emailSendingManager;

    @InjectMocks
    EditorRegistration sut;


    @Nested
    class GivenValidEditor {

        @BeforeEach
        void init() {
            when(editorStorage.save(any(Editor.class)))
                    .thenAnswer( invocation -> {
                        Editor editorReturned = invocation.getArgument(0, Editor.class);
                        editorReturned.setId(76L);
                        return editorReturned;
                    });

        }

        @Test
        void whenCreate_returnId() {
            Editor savedEditor = sut.create(editor);
            assertEquals(76L, savedEditor.getId());
        }

        @Test
        void whenCreate_thenCallEditorStorageSave(){
            sut.create(editor);
            verify(editorStorage).save(editor);
        }

        @Test
        void whenCreateThrowsException_thenDoNotSendEmail(){
            when(editorStorage.save(editor))
                    .thenThrow(new RuntimeException());
            assertAll(
                    () -> assertThrows(RuntimeException.class, () -> sut.create(editor)),
                    () -> verify(emailSendingManager, never()).sendEmail(any())
            );
        }

        @Test
        void whenCreate_thenSendEmailToEditor(){
            ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
            Editor editorSaved = sut.create(editor);
            verify(emailSendingManager).sendEmail(messageArgumentCaptor.capture());
            Message message = messageArgumentCaptor.getValue();
            assertEquals(editorSaved.getEmail(), message.getRecipient());
        }

        @Test
        void whenCreate_thenVerifyEmail(){
            sut.create(editor);
            verify(editor, atLeastOnce()).getEmail();
        }

        @Test
        void andWithEmail_whenCreate_thenThrowException(){
            when(editorStorage.findByEmail("asd@asd.com"))
                    .thenReturn(Optional.empty()) // first call
                    .thenReturn(Optional.of(editor)); // second call
            Editor editorWithEmail = new Editor(null, "Nome", "asd@asd.com", BigDecimal.TEN, true);
            sut.create(editor); // first call
            assertThrows(BusinessRuleException.class, () -> sut.create(editorWithEmail)); // second call
        }

        @Test
        void whenCreate_thenFirstSaveAndSecondSendEmail(){
            sut.create(editor);
            InOrder inOrder = inOrder(editorStorage, emailSendingManager);
            inOrder.verify(editorStorage).save(editor);
            inOrder.verify(emailSendingManager).sendEmail(any(Message.class));
        }

    }


    @Test
    void givenNulLEditor_whenCreate_thenThrowException(){
        Executable create = () -> sut.create(null);
        assertThrows(RuntimeException.class, create);
        verify(editorStorage, never()).save(any());
        verify(emailSendingManager, never()).sendEmail(any());
    }

}