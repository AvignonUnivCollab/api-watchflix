package com.streaming.watchfilx.dtos.responses.auth;

import com.streaming.watchfilx.models.User;

public class LoginResponse {

    private String message;
    private String email;
    private String nom;
    private String prenom;
    private String role;

    public LoginResponse(String message, User user) {
        this.message = message;
        this.email = user.getAdresseMail();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.role = user.getRole().name();
    }

    public String getMessage() { return message; }
    public String getEmail() { return email; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getRole() { return role; }
}
