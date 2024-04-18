package com.silveo.copypaste.controllers;

import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.services.AuthorDetailsService;
import com.silveo.copypaste.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/new-author")
    public String addAuthor(@RequestBody Author author){
        authorService.addAuthor(author);
        return "Author " + author.getUsername() + " was registered!";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<Author> findAllAuthors(){
        return authorService.findAllAuthors();
    }
}
