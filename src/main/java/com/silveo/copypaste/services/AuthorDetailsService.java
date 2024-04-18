package com.silveo.copypaste.services;

import com.silveo.copypaste.config.AuthorDetails;
import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorDetailsService implements UserDetailsService {
    @Autowired
    private AuthorRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Author> author = repository.findByUsername(username);
        return author.map(AuthorDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
