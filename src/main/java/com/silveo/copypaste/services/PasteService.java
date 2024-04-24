package com.silveo.copypaste.services;

import com.silveo.copypaste.entity.Paste;
import com.silveo.copypaste.repositories.PasteRepository;
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

    public Paste getPasteById(Long id){
        return repository.findPasteById(id);
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
