package com.algaworks.junit.blog.business;

import com.algaworks.junit.blog.exception.BusinessRuleException;
import com.algaworks.junit.blog.exception.PostNotFoundException;
import com.algaworks.junit.blog.model.Editor;
import com.algaworks.junit.blog.model.Notification;
import com.algaworks.junit.blog.model.Post;
import com.algaworks.junit.blog.storage.PostStorage;
import com.algaworks.junit.blog.utility.SlugConverter;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
class PostRegistrationTest {
    @Mock
    PostStorage postStorage;

    @Mock
    EarningsCalculator earningsCalculator;

    @Mock
    NotificationManager notificationManager;


    @Spy
    Editor editor = new Editor("Nome", "email@provider.com", BigDecimal.TEN, false);
    @Spy
    Post post = new Post("Title", "Content", editor, false, false);

    @InjectMocks
    PostRegistration sut;

    @Nested
    class GivenNullPost {

        @Test
        void whenCreate_whenThrowException() {
            Executable create = () -> sut.create(null);
            assertThrows(NullPointerException.class, create);
        }

        @Test
        void whenEdit_whenThrowException() {
            Executable edit = () -> sut.edit(null);
            assertThrows(NullPointerException.class, edit);
        }

    }

    @Nested
    class GivenPost {


        @BeforeEach
        void init(){
            Post postSaved = new Post("Title", "Content", editor, false, false);
            postSaved.setId(1L);
            when(postStorage.save(any(Post.class))).thenReturn(postSaved);
        }

        @Nested
        class WhenCreate {
            @Test
            void useCorrectOrder(){
                try (MockedStatic<SlugConverter> mockedStatic = mockStatic(SlugConverter.class)){
                    InOrder inOrder = inOrder(earningsCalculator, postStorage, notificationManager);

                    sut.create(post);

                    mockedStatic.verify(() -> SlugConverter.convertWithCode(anyString()));
                    inOrder.verify(earningsCalculator).calculate(any(Post.class));
                    inOrder.verify(postStorage).save(any(Post.class));
                    inOrder.verify(notificationManager).send(any(Notification.class));

                }
            }
        }

        @Nested
        class WhenEdit{

            @Test
            void thenStorageSaves(){
                post.setId(1L);
                when(postStorage.findById(anyLong())).thenReturn(Optional.of(post));

                sut.edit(post);

                verify(postStorage).save(any(Post.class));

            }

            @Test
            void thenPostUpdateWithData(){
                post.setId(1L);
                when(postStorage.findById(anyLong())).thenReturn(Optional.of(post));

                sut.edit(post);

                verify(post).updateWithData(post);
            }

            @Test
            void paidPost_thenPostUpdateWithData(){
                post.setId(1L);
                when(postStorage.findById(anyLong())).thenReturn(Optional.of(post));
                when(post.isPaid()).thenReturn(false);

                sut.edit(post);

                verify(post).setEarnings(any());
                verify(earningsCalculator).calculate(any(Post.class));
            }

        }

    }

    @Nested
    class GivenLong{


        @Test
        void andIsNull_whenRemove_thenThrowException() {
            Executable remove = () -> sut.remove(null);
            assertThrows(NullPointerException.class, remove);
        }

        @Nested
        class whenRemove{

            @Test
            void andStorageReturnEmpty_thenThrowPostNotFoundException(){
                when(postStorage.findById(anyLong())).thenReturn(Optional.empty());
                Executable remove = () -> sut.remove(1L);
                assertThrows(PostNotFoundException.class, remove);
            }

            @Test
            void whenPostIsPublished_whenThrowBusinessRuleException() {
                when(postStorage.findById(anyLong())).thenReturn(Optional.of(post));
                when(post.isPublished()).thenReturn(true);
                Executable remove = () -> sut.remove(1L);
                BusinessRuleException exception = assertThrows(BusinessRuleException.class, remove);
                assertEquals("A published post cannot be removed", exception.getMessage());
            }

            @Test
            void whenPostIsPaid_whenThrowBusinessRuleException(){
                when(postStorage.findById(anyLong())).thenReturn(Optional.of(post));
                when(post.isPaid()).thenReturn(true);
                Executable remove = () -> sut.remove(1L);
                BusinessRuleException exception = assertThrows(BusinessRuleException.class, remove);
                assertEquals("A paid post cannot be removed", exception.getMessage());
            }

            @Test
            void thenStorageRemove(){
                when(postStorage.findById(anyLong())).thenReturn(Optional.of(post));
                sut.remove(1L);
                verify(postStorage).remove(1L);
            }


        }


    }
}