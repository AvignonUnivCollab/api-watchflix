package com.streaming.watchfilx.models;

import jakarta.persistence.*;
//import lombok.Data;

@Entity
//@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String adresseMail;
    private String motDePasse;


    @Enumerated(EnumType.STRING)
    private Role role;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}
