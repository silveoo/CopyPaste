package com.silveo.copypaste.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String confirmationToken;

    private Boolean emailConfirmed;

    private String password;

    private String role;

    private LocalDate registrationDate;

    //sets author.registrationDate to now
    @PrePersist
    public void prePersist() {
        this.registrationDate = LocalDate.now();
    }
}
