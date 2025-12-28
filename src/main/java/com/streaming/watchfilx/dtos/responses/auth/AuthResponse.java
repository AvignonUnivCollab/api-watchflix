package com.streaming.watchfilx.dtos.responses.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.streaming.watchfilx.models.User;

public class AuthResponse {
    @JsonProperty("id")
    private Long id;

    private String message;
    private String email;
    private String nom;
    private String prenom;
    private String role;

    public AuthResponse(String message, User user) {
        this.message = message;
        this.id = user.getId();
        this.email = user.getEmail();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.role = user.getRole().name();
    }

    public String getMessage() { return message; }
    public String getEmail() { return email; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getRole() { return role; }
    public Long getId() { return id; }
}
