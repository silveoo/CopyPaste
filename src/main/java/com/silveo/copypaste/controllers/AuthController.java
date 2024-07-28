package com.silveo.copypaste.controllers;

import com.silveo.copypaste.dto.AuthorInfoDTO;
import com.silveo.copypaste.entity.Author;
import com.silveo.copypaste.repositories.AuthorRepository;
import com.silveo.copypaste.services.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthorRepository authorRepository;

    //logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return ResponseEntity.ok("Successfully logged out");
        } else {
            return ResponseEntity.badRequest().body("No active session");
        }
    }

    //username and email or 400
    @GetMapping("/check")
    public ResponseEntity<AuthorInfoDTO> isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null &&
                auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);
        if(isAuthenticated) {
            Author author = authorRepository.findAuthorByUsername(auth.getName());
            AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO(author.getUsername(), author.getEmail());
            return ResponseEntity.ok(authorInfoDTO);
        }
        else return ResponseEntity.badRequest().body(null);
    }


}

