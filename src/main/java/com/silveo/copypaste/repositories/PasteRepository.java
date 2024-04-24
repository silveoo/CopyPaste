package com.silveo.copypaste.repositories;

import com.silveo.copypaste.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
    Paste findPasteById(Long id);

    List<Paste> findAllByAuthor(String username);
}
