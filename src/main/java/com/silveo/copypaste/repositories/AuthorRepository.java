package com.silveo.copypaste.repositories;

import com.silveo.copypaste.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByUsername(String username);

    Author findByConfirmationToken(String token);

    Author findAuthorByUsername(String username);
}
