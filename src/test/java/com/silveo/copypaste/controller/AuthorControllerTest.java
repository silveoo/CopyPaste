package com.silveo.copypaste.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.silveo.copypaste.controllers.AuthorController;
import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    @WithMockUser(roles="ROLE_ADMIN")
    public void testFindAllAuthors() throws Exception {
        // подготовка
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());

        when(authorService.findAllAuthors()).thenReturn(authors);

        // выполнение и проверка
        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(authorService).findAllAuthors();
    }

}
