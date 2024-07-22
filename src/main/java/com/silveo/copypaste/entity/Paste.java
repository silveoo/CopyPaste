package com.silveo.copypaste.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String text;
    private String author;
    private LocalDate creationDate;
    private Long views = 0L;
    private Long commentCount = 0L;

    //comment list
    @OneToMany(mappedBy = "paste", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference //removes duplicates
    private List<Comment> comments = new ArrayList<>();

    //sets author to currently logged author and date to now
    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDate.now();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            this.author = auth.getName();
        }
    }
}
