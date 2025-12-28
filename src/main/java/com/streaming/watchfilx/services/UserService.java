package com.streaming.watchfilx.services;

import com.streaming.watchfilx.models.Role;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // -----------------------
    // INSCRIPTION
    // -----------------------
    public User register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    // -----------------------
    // CONNEXION
    // -----------------------
    public User login(String email, String password) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email introuvable"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return user;
    }

    // -----------------------
    // RÉCUPÉRER UN UTILISATEUR PAR ID ✅ NOUVEAU
    // -----------------------
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    // -----------------------
    // CRÉER UN TOKEN DE RESET
    // -----------------------
    public String createResetToken(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email introuvable"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        userRepository.save(user);

        return token;
    }

    // -----------------------
    // RÉINITIALISER LE MOT DE PASSE
    // -----------------------
    public String resetPassword(String token, String newPassword) {

        User user = userRepository
                .findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);

        userRepository.save(user);

        return "Mot de passe réinitialisé avec succès";
    }

    // -----------------------
    // GETTERS (si utilisés ailleurs)
    // -----------------------
    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
