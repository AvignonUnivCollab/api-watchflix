package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.dtos.requests.auth.ResetPasswordRequest;
import com.streaming.watchfilx.dtos.responses.auth.AuthResponse;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.dtos.requests.auth.ForgotPasswordRequest;
import com.streaming.watchfilx.dtos.requests.auth.LoginRequest;
import com.streaming.watchfilx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody User user) {
        User res = userService.register(user);
        return new AuthResponse("Compte créer", user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        System.out.println("ID USER = " + user.getId());
        return new AuthResponse(" réussie", user);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
        boolean exists = userService
                .getUserRepository()
                .findByEmail(request.getEmail())
                .isPresent();

        if (!exists) {
            return "Aucun compte associé à cet email";
        }

        String token = userService.createResetToken(request.getEmail());

        return "Token généré : " + token;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(request.getToken(), request.getNewPassword());
    }


}
