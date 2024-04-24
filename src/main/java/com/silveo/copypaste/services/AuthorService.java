package com.silveo.copypaste.services;

import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepository repository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    public Author addAuthor(Author author){
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        String token = UUID.randomUUID().toString();
        author.setConfirmationToken(token);
        author.setEmailConfirmed(false);
        emailService.sendConfirmationEmail(author.getEmail(), token);
        return repository.save(author);
    }

    public List<Author> findAllAuthors(){
        return repository.findAll();
    }

    public boolean confirmToken(String token){
        Author author = repository.findByConfirmationToken(token);
        if(author != null){
            author.setEmailConfirmed(true);
            author.setConfirmationToken(null);
            repository.save(author);
            return true;
        }
        return false;
    }
}
