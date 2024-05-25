package com.silveo.copypaste.services;

import com.silveo.copypaste.entity.Comment;
import com.silveo.copypaste.entity.Paste;
import com.silveo.copypaste.repositories.PasteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Primary
public class PasteService {
    private final PasteRepository repository;
    private final CommentService commentService;

    //finding all pastes
    public List<Paste> findAll(){
        List<Paste> pastes = repository.findAll();
        pastes.forEach(paste -> paste.setComments(new ArrayList<>())); //making comments invisible in /paste
        return pastes;
    }

    //paste by its own id
    @Transactional
    public Paste getPasteById(Long id) {
        Paste paste = repository.findPasteById(id);
        if (paste != null) {
            if (paste.getViews() == null) {
                paste.setViews(0L);
            }
            paste.setViews(paste.getViews() + 1);
            repository.save(paste);
        }
        return paste;
    }

    //adding comment to paste
    @Transactional
    public void addCommentToPaste(Long pasteId, Comment comment) {
        Paste paste = repository.findById(pasteId).orElseThrow(() -> new EntityNotFoundException("Paste not found"));
        comment.setPaste(paste);
        commentService.addComment(comment); // saving to db
        paste.getComments().add(comment); // saving to colletion
        repository.save(paste); // saving
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
