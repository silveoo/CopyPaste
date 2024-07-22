package com.silveo.copypaste.services;

import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.entity.Comment;
import com.silveo.copypaste.entity.Paste;
import com.silveo.copypaste.repositories.AuthorRepository;
import com.silveo.copypaste.repositories.PasteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
@Primary
public class PasteService {
    private final PasteRepository repository;
    private final AuthorRepository authorRepository;
    private final CommentService commentService;
    private final EmailService emailService;

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

        paste.setCommentCount((long) paste.getComments().size());
        repository.save(paste); // saving

        //email sending to pastes author
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //check if the pastes author is comments author
        if(!Objects.equals(authentication.getName(), paste.getAuthor())) {
            Author author = authorRepository.findAuthorByUsername(paste.getAuthor());
            String username = authentication.getName();
            emailService.sendNewCommentEmail(author.getEmail(), username, pasteId);
        }
    }

    //deleting paste + sending an email
    public void deletePaste(Long id){
        Paste paste = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paste not found"));
        repository.deleteById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //check if the pastes author is comments author
        if(!Objects.equals(authentication.getName(), paste.getAuthor())) {
            String adminName = authentication.getName();
            Author author = authorRepository.findAuthorByUsername(paste.getAuthor());
            emailService.sendDeleteEmail(author.getEmail(), adminName, id);
        }
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
