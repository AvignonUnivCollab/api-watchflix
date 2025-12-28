package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.dtos.responses.user.UserResponse;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserResponse(user);
    }
}
