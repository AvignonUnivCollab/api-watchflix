package com.streaming.watchfilx;

import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.models.Role;
import com.streaming.watchfilx.services.RoomService;
import com.streaming.watchfilx.repositories.UserRepository;
import com.streaming.watchfilx.models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;

    // -----------------------
    // TEST createRoom
    // -----------------------
    @Test
    void createRoom_shouldSaveRoom() {
        CreateRoomRequest request = new CreateRoomRequest();
        request.setName("Salon-" + System.nanoTime());
        request.setCreatorId(1L);

        // createRoom retourne un DTO (pas l'entity Room)
        var createdRoom = roomService.createRoom(request, null);

        assertThat(createdRoom).isNotNull();
        assertThat(createdRoom.getId()).isNotNull();

        // Vérification métier via l'entity
        Room roomEntity = roomService.getRoomById(createdRoom.getId());
        assertThat(roomEntity.getMembers()).isEqualTo(0);
    }

    // -----------------------
    // TEST joinRoom
    // -----------------------
    @Test
    void joinRoom_shouldAddUserToRoom() {
        User user = new User();
        user.setNom("Test");
        user.setPrenom("User");
        user.setEmail("user" + System.nanoTime() + "@mail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        user = userRepository.save(user);

        CreateRoomRequest request = new CreateRoomRequest();
        request.setName("Join-" + System.nanoTime());
        request.setCreatorId(1L);

        var createdRoom = roomService.createRoom(request, null);

        String result = roomService.joinRoom(createdRoom.getId(), user.getId());

        assertThat(result).contains("succès");
        assertThat(roomService.getRoomById(createdRoom.getId()).getMembers()).isEqualTo(1);
    }

    // -----------------------
    // TEST leaveRoom
    // -----------------------
    @Test
    void leaveRoom_shouldRemoveUserFromRoom() {
        User user = new User();
        user.setNom("Leave");
        user.setPrenom("User");
        user.setEmail("leave" + System.nanoTime() + "@mail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        user = userRepository.save(user);

        CreateRoomRequest request = new CreateRoomRequest();
        request.setName("Leave-" + System.nanoTime());
        request.setCreatorId(1L);

        var createdRoom = roomService.createRoom(request, null);

        roomService.joinRoom(createdRoom.getId(), user.getId());

        String result = roomService.leaveRoom(createdRoom.getId(), user.getId());

        assertThat(result).contains("succès");
        assertThat(roomService.getRoomById(createdRoom.getId()).getMembers()).isEqualTo(0);
    }

    // -----------------------
    // TEST removeMember
    // -----------------------
    @Test
    void removeMember_shouldRemoveUserByCreator() {
        User creator = new User();
        creator.setNom("Creator");
        creator.setPrenom("User");
        creator.setEmail("creator" + System.nanoTime() + "@mail.com");
        creator.setPassword("password");
        creator.setRole(Role.USER);
        creator = userRepository.save(creator);

        User member = new User();
        member.setNom("Member");
        member.setPrenom("User");
        member.setEmail("member" + System.nanoTime() + "@mail.com");
        member.setPassword("password");
        member.setRole(Role.USER);
        member = userRepository.save(member);

        CreateRoomRequest request = new CreateRoomRequest();
        request.setName("Remove-" + System.nanoTime());
        request.setCreatorId(creator.getId());

        var createdRoom = roomService.createRoom(request, null);

        roomService.joinRoom(createdRoom.getId(), member.getId());

        String result = roomService.removeMember(
                createdRoom.getId(),
                creator.getId(),
                member.getId()
        );

        assertThat(result).contains("succès");
        assertThat(roomService.getRoomById(createdRoom.getId()).getMembers()).isEqualTo(0);
    }
}
