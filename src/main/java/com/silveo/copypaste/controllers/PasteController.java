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
import java.util.Objects;

@RestController
@RequestMapping("api/v1/paste")
@AllArgsConstructor
public class PasteController {

    private final PasteService pasteService;
    private static final Logger logger = LoggerFactory.getLogger(PasteController.class);

    //adds new paste, req to be authed
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public String addPaste(@RequestBody Paste paste){
        pasteService.addPaste(paste);
        logger.info("New paste with ID: {} has been created by {}!", paste.getId(), paste.getAuthor());
        return "Paste saved! Its URL: localhost:8080/api/v1/paste/" + paste.getId();
    }

    //finds list of all pastes, without auth
    @GetMapping("")
    public List<Paste> findAllPastes(){
        return pasteService.findAll();
    }

    //searching paste by its id with comments(comments realized in service), req to be authed
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public Paste findPasteById(@PathVariable Long id){
        return pasteService.getPasteById(id);
    }

    //removing paste, only admin and paste's author can
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePaste(@PathVariable Long id){
        Paste paste = pasteService.getPasteById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedIn = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if((Objects.equals(paste.getAuthor(), currentLoggedIn)) || isAdmin){
            logger.info("Paste with ID: {} was deleted by {}", id, currentLoggedIn);
            pasteService.deletePaste(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        else{
            logger.info("There was an attempt to delete a paste with ID: {} by {}, but there were not enought permissions", id, currentLoggedIn);
            return new ResponseEntity<>("No permission", HttpStatus.FORBIDDEN);
        }
    }

    //all pastes made by author
    @GetMapping("/author/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public List<Paste> findAllByAuthor(@PathVariable String username){
        return pasteService.findAllByAuthor(username);
    }

    //updating paste, only admin and paste's author can
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
