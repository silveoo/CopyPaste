package com.silveo.copypaste.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

@Entity
@Data
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String author;
    private LocalDate creationDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDate.now(); // Устанавливаем сегодняшнюю дату
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            this.author = auth.getName(); // Устанавливаем имя текущего пользователя
        }
    }
}
