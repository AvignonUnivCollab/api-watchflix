package com.streaming.watchfilx;

import com.streaming.watchfilx.models.Role;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.repositories.RoomRepository;
import com.streaming.watchfilx.repositories.UserRepository;
import com.streaming.watchfilx.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void contextLoads() {
        // Vérifie que le contexte Spring démarre correctement
    }

    @Test
    void createUser_shouldSaveUser() {

        User user = new User();
        user.setEmail("testuser@mail.com");
        user.setNom("Test");
        user.setPrenom("User");
        user.setPassword("password");
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
    }
}
