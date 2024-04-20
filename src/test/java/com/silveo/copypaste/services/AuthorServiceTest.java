package com.silveo.copypaste.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.repositories.AuthorRepository;
import com.silveo.copypaste.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void testFindAllAuthors() {
        // подготовка
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());

        when(authorRepository.findAll()).thenReturn(authors);

        // выполнение
        List<Author> result = authorService.findAllAuthors();

        // проверка
        assertEquals(authors, result);
        verify(authorRepository).findAll();
    }

}
