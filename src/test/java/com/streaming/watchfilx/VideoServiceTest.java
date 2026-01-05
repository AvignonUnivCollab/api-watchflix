package com.streaming.watchfilx;

import com.streaming.watchfilx.models.*;
import com.streaming.watchfilx.repositories.*;
import com.streaming.watchfilx.services.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void removeVideoFromRoom_shouldSetRoomIdToNull() {

        // ---------- USER ----------
        User user = new User();
        user.setNom("Video");
        user.setPrenom("User");
        user.setEmail("video_" + System.nanoTime() + "@mail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        user = userRepository.save(user);

        // ---------- ROOM ----------
        Room room = new Room();
        room.setName("RoomVideo");
        room.setCreatorId(user.getId());
        room = roomRepository.save(room);

        // ---------- VIDEO ----------
        Video video = new Video();
        video.setTitle("Test video");          
        video.setDuration(120);               
        video.setUrl("http://video.test");
        video.setDescription("Test video");
        video.setThumbnail("thumb.png");
        video.setUserId(user.getId());         
        video.setRoomId(room.getId());          
        video = videoRepository.save(video);

        // ---------- ACTION ----------
        videoService.removeVideoFromRoom(video.getId());

        // ---------- VERIFY ----------
        Video updated = videoRepository.findById(video.getId()).orElseThrow();
        assertThat(updated.getRoomId()).isNull();
    }
}
