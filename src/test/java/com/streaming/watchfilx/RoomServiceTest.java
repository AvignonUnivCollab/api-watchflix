package com.streaming.watchfilx;

import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.services.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    void createRoom_shouldSaveRoom() {
        // GIVEN : un salon avec un nom unique
        Room room = new Room();
        room.setName("Salon Test " + System.currentTimeMillis());
        room.setCreatorId(1L);
        room.setDescription("Salon de test");

        // WHEN : on crée le salon
        Room savedRoom = roomService.createRoom(room);

        // THEN : le salon est bien créé
        assertNotNull(savedRoom.getId());
        assertEquals(room.getName(), savedRoom.getName());
    }

    @Test
    void joinRoom_shouldAddUserToRoom() {
        // GIVEN : un salon existant
        Room room = new Room();
        room.setName("Salon Join " + System.currentTimeMillis());
        room.setCreatorId(1L);
        room.setDescription("Salon pour test join");

        Room savedRoom = roomService.createRoom(room);

        // GIVEN : un utilisateur existant
        Long userId = 1L;

        // WHEN : l’utilisateur rejoint le salon
        String result = roomService.joinRoom(savedRoom.getId(), userId);

        // THEN : message OK
        assertEquals("Utilisateur ajouté au salon avec succès", result);

        // THEN : le nombre de membres a augmenté
        Room updatedRoom = roomService.getRoomById(savedRoom.getId());
        assertEquals(1, updatedRoom.getMembers());
    }

    @Test
    void leaveRoom_shouldRemoveUserFromRoom() {
        // GIVEN : un salon existant
        Room room = new Room();
        room.setName("Salon Leave " + System.currentTimeMillis());
        room.setCreatorId(1L);
        room.setDescription("Salon pour test leave");

        Room savedRoom = roomService.createRoom(room);

        // GIVEN : un utilisateur existant
        Long userId = 1L;

        // GIVEN : l’utilisateur rejoint d’abord le salon
        roomService.joinRoom(savedRoom.getId(), userId);


    }
}
