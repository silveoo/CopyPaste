package com.silveo.copypaste.controllers;

import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.services.AuthorDetailsService;
import com.silveo.copypaste.services.AuthorService;
import com.silveo.copypaste.services.EmailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/author")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @PostMapping("/new-author")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        String token = UUID.randomUUID().toString();
        author.setConfirmationToken(token);
        author.setEmailConfirmed(false);
        Author savedAuthor = authorService.addAuthor(author);
        emailService.sendConfirmationEmail(author.getEmail(), token);
        logger.info("New Author ({}) has been registered!", author.getUsername());
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<Author> findAllAuthors(){
        return authorService.findAllAuthors();
    }
}
