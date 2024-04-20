package com.silveo.copypaste.services;

import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepository repository;
    private PasswordEncoder passwordEncoder;

    public Author addAuthor(Author author){
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        return repository.save(author);
    }

    public List<Author> findAllAuthors(){
        return repository.findAll();
    }
    
}
