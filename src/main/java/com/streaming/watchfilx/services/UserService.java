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

    public User register(User user) {

        if (userRepository.findByAdresseMail(user.getAdresseMail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    // GETTERS pour login
    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public User login(String email, String password) {

        // Vérifie si email existe
        User user = userRepository.findByAdresseMail(email)
                .orElseThrow(() -> new RuntimeException("Email introuvable"));

        // Vérifie si le mot de passe correspond
        if (!passwordEncoder.matches(password, user.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return user; // Connexion réussie
    }


    public String createResetToken(String email) {

        User user = userRepository.findByAdresseMail(email)
                .orElseThrow(() -> new RuntimeException("Email introuvable"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        userRepository.save(user);

        return token;
    }


    public String resetPassword(String token, String newPassword) {

        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        user.setMotDePasse(passwordEncoder.encode(newPassword));

        // Le token n'est plus valide une fois utilisé
        user.setResetToken(null);

        userRepository.save(user);

        return "Mot de passe réinitialisé avec succès";
    }


}
