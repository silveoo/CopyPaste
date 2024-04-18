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
    public void testAddAuthor() {
        // подготовка
        Author author = new Author();
        author.setUsername("testUser");
        author.setPassword("testPass");
        author.setRole("ROLE_AUTHOR");

        Author savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setUsername("testUser");
        savedAuthor.setPassword("encodedPass");
        savedAuthor.setRole("ROLE_AUTHOR");

        when(passwordEncoder.encode(author.getPassword())).thenReturn("encodedPass");
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // выполнение
        Author result = authorService.addAuthor(author);

        // проверка
        assertEquals(savedAuthor, result);
        verify(passwordEncoder).encode("testPass");
        verify(authorRepository).save(author);
    }

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
