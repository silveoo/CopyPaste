package com.silveo.copypaste.controllers;

import com.silveo.copypaste.entity.Paste;
import com.silveo.copypaste.services.PasteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/paste")
@AllArgsConstructor
public class PasteController {

    private final PasteService pasteService;
    private static final Logger logger = LoggerFactory.getLogger(PasteController.class);

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public String addPaste(@RequestBody Paste paste){
        pasteService.addPaste(paste);
        logger.info("New paste with ID: {} has been created by {}!", paste.getId(), paste.getAuthor());
        return "Paste saved! Its URL: localhost:8080/api/v1/paste/" + paste.getId();
    }

    @GetMapping("")
    public List<Paste> findAllPastes(){
        return pasteService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public Paste findPasteById(@PathVariable Long id){
        return pasteService.getPasteById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public void deletePaste(@PathVariable Long id){
        logger.info("Paste with ID: {} was deleted", id);
        pasteService.deletePaste(id);
    }

    @GetMapping("/author/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public List<Paste> findAllByAuthor(@PathVariable String username){
        return pasteService.findAllByAuthor(username);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_AUTHOR') and #paste.author == authentication.name)")
    public ResponseEntity<Paste> updatePaste(@RequestBody Paste paste) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedIn = authentication.getName();
        Paste updatedPaste = pasteService.updatePaste(paste);
        if (updatedPaste != null) {
            logger.info("Paste with ID: {} was updated by {}!", paste.getId(), currentLoggedIn);
            return new ResponseEntity<>(updatedPaste, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
