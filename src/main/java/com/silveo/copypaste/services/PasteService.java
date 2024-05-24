package com.silveo.copypaste.services;

import com.silveo.copypaste.entity.Paste;
import com.silveo.copypaste.repositories.PasteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Primary
public class PasteService {
    private final PasteRepository repository;

    public List<Paste> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Paste getPasteById(Long id){
        Paste paste = repository.findPasteById(id);
        if(paste != null) {
            if(paste.getViews() == null)
                paste.setViews(0L);
            paste.setViews(paste.getViews() + 1);
            repository.save(paste);
        }
        return paste;
    }

    public void deletePaste(Long id){
        repository.deleteById(id);
    }

    public Paste updatePaste(Paste paste){
        return repository.save(paste);
    }

    public List<Paste> findAllByAuthor(String username){
        return repository.findAllByAuthor(username);
    }

    public void addPaste(Paste paste){
        repository.save(paste);
    }

}
