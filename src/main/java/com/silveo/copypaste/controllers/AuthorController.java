package com.silveo.copypaste.controllers;

import com.silveo.copypaste.dto.AuthorInfoDTO;
import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.repositories.AuthorRepository;
import com.silveo.copypaste.services.AuthorService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorRepository authorRepository;

    //adds author new author (without auth)
    @PostMapping("/new-author")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        Author savedAuthor = authorService.addAuthor(author);
        logger.info("New Author ({}) has been registered!", author.getUsername());
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    //confirms token - email confirmation
    @GetMapping("/confirm/{token}")
    public ResponseEntity<String> confirmRegistration(@PathVariable String token){
        boolean isConfirmed = authorService.confirmToken(token);
        if(isConfirmed) {
            logger.info("User confirmed his email");
            return ResponseEntity.ok("Email подтвержден!");
        }
        else {
            return ResponseEntity.badRequest().body("Неверный токен подтверждения!");
        }
    }

    @GetMapping("/check")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR', 'ROLE_ADMIN')")
    public ResponseEntity<AuthorInfoDTO> getAuthorInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findAuthorByUsername(auth.getName());
        AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO(author.getUsername(), author.getEmail());
        return new ResponseEntity<>(authorInfoDTO, HttpStatus.OK);
    }

    //all authors list (admin only)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<Author> findAllAuthors(){
        return authorService.findAllAuthors();
    }
}
