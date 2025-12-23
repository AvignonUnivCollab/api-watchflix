package com.streaming.watchfilx;

import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.models.Role;
import com.streaming.watchfilx.services.RoomService;
import com.streaming.watchfilx.repositories.UserRepository;
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
        Room room = new Room();
        room.setName("Salon-" + System.nanoTime());
        room.setCreatorId(1L);

        Room savedRoom = roomService.createRoom(room);

        assertThat(savedRoom.getId()).isNotNull();
        assertThat(savedRoom.getMembers()).isEqualTo(0);
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

        Room room = new Room();
        room.setName("Join-" + System.nanoTime());
        room.setCreatorId(1L);
        room = roomService.createRoom(room);

        String result = roomService.joinRoom(room.getId(), user.getId());

        assertThat(result).contains("succès");
        assertThat(roomService.getRoomById(room.getId()).getMembers()).isEqualTo(1);
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

        Room room = new Room();
        room.setName("Leave-" + System.nanoTime());
        room.setCreatorId(1L);
        room = roomService.createRoom(room);

        roomService.joinRoom(room.getId(), user.getId());

        String result = roomService.leaveRoom(room.getId(), user.getId());

        assertThat(result).contains("succès");
        assertThat(roomService.getRoomById(room.getId()).getMembers()).isEqualTo(0);
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

        Room room = new Room();
        room.setName("Remove-" + System.nanoTime());
        room.setCreatorId(creator.getId());
        room = roomService.createRoom(room);

        roomService.joinRoom(room.getId(), member.getId());

        String result = roomService.removeMember(
                room.getId(),
                creator.getId(),
                member.getId()
        );

        assertThat(result).contains("succès");
        assertThat(roomService.getRoomById(room.getId()).getMembers()).isEqualTo(0);
    }
}
