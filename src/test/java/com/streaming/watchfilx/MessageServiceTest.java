package com.streaming.watchfilx;

import com.streaming.watchfilx.models.Message;
import com.streaming.watchfilx.models.Role;
import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.repositories.RoomRepository;
import com.streaming.watchfilx.repositories.UserRepository;
import com.streaming.watchfilx.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    // -----------------------
    // CONTEXT
    // -----------------------
    @Test
    void contextLoads() {
        // Vérifie que Spring démarre
    }

    // -----------------------
    // SEND MESSAGE
    // -----------------------
    @Test
    void sendMessage_shouldSaveMessage() {

        //  Créer utilisateur
        User user = new User();
        user.setEmail("msg_" + UUID.randomUUID() + "@mail.com");
        user.setNom("Message");
        user.setPrenom("User");
        user.setPassword("password");
        user.setRole(Role.USER);
        user = userRepository.save(user);

        //  Créer salon
        Room room = new Room();
        room.setName("RoomMsg_" + UUID.randomUUID());
        room.setCreatorId(user.getId());
        room = roomRepository.save(room);

        // Envoyer message (ASCII ONLY)
        Message message = messageService.sendMessage(
                room.getId(),
                user.getId(),
                "Hello Watchflix"
        );

        //  Vérifications
        assertNotNull(message.getId());
        assertThat(message.getContent()).isEqualTo("Hello Watchflix");
        assertThat(message.getRoomId()).isEqualTo(room.getId());
    }
}
