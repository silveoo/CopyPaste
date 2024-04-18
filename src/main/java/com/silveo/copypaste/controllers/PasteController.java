package com.silveo.copypaste.controllers;

import com.silveo.copypaste.entity.Paste;
import com.silveo.copypaste.services.PasteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/paste")
@AllArgsConstructor
public class PasteController {

    private final PasteService pasteService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public String addPaste(@RequestBody Paste paste){
        pasteService.addPaste(paste);
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
        pasteService.deletePaste(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_AUTHOR') and #paste.author == authentication.name)")
    public ResponseEntity<Paste> updatePaste(@RequestBody Paste paste) {
        Paste updatedPaste = pasteService.updatePaste(paste);
        if (updatedPaste != null) {
            return new ResponseEntity<>(updatedPaste, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
