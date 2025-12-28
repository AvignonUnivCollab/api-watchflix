package com.streaming.watchfilx.dtos.responses.user;

import com.streaming.watchfilx.models.Role;
import com.streaming.watchfilx.models.User;

public class UserResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}
