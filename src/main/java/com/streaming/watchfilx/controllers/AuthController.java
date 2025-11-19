package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    // ============================
    //         REGISTER
    // ============================
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // ============================
    //           LOGIN
    // ============================
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userService.login(request.getEmail(), request.getPassword());

        return new LoginResponse("Connexion réussie", user);
    }

    // ============================
    //      FORGOT PASSWORD
    // ============================
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {

        boolean exists = userService
                .getUserRepository()
                .findByAdresseMail(request.getEmail())
                .isPresent();

        if (!exists) {
            return "Aucun compte associé à cet email";
        }

        String token = userService.createResetToken(request.getEmail());

        return "Token généré : " + token;
    }

    // ============================
    //      RESET PASSWORD
    // ============================
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(request.getToken(), request.getNewPassword());
    }

    // ============================
    //     DTOs (Request / Response)
    // ============================

    static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    static class ForgotPasswordRequest {
        private String email;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    static class ResetPasswordRequest {
        private String token;
        private String newPassword;

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    static class LoginResponse {
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
}
