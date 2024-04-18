package com.silveo.copypaste.repositories;

import com.silveo.copypaste.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
    public Paste findPasteById(Long id);

    public List<Paste> findAllByAuthor(String username);
}
