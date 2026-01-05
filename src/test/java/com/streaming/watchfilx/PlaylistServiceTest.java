package com.streaming.watchfilx;

import com.streaming.watchfilx.models.*;
import com.streaming.watchfilx.repositories.*;
import com.streaming.watchfilx.services.PlaylistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PlaylistServiceTest {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Test
    void addVideo_shouldAddVideoToPlaylist() {

        // ---------- USER ----------
        User user = new User();
        user.setNom("Playlist");
        user.setPrenom("User");
        user.setEmail("playlist_" + System.nanoTime() + "@mail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        user = userRepository.save(user);

        // ---------- ROOM ----------
        Room room = new Room();
        room.setName("RoomPlaylist");
        room.setCreatorId(user.getId());
        room = roomRepository.save(room);

        // ---------- VIDEO ----------
        Video video = new Video();
        video.setTitle("Playlist video");       
        video.setDuration(200);                
        video.setUrl("http://playlist.video");
        video.setDescription("Playlist video");
        video.setThumbnail("thumb.png");
        video.setUserId(user.getId());          
        video.setRoomId(room.getId());          
        video = videoRepository.save(video);

        // ---------- ACTION ----------
        playlistService.addVideo(room.getId(), video.getId(), user.getId());

        // ---------- VERIFY ----------
        assertThat(
                playlistRepository.existsByRoomIdAndVideoId(room.getId(), video.getId())
        ).isTrue();
    }

    @Test
    void removeVideo_shouldRemoveVideoFromPlaylist() {

        // ---------- USER ----------
        User user = new User();
        user.setNom("Remove");
        user.setPrenom("User");
        user.setEmail("remove_" + System.nanoTime() + "@mail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        user = userRepository.save(user);

        // ---------- ROOM ----------
        Room room = new Room();
        room.setName("RoomRemove");
        room.setCreatorId(user.getId());
        room = roomRepository.save(room);

        // ---------- VIDEO ----------
        Video video = new Video();
        video.setTitle("Remove video");          
        video.setDuration(180);               
        video.setUrl("http://remove.video");
        video.setDescription("Remove video");
        video.setThumbnail("thumb.png");
        video.setUserId(user.getId());          
        video.setRoomId(room.getId());          
        video = videoRepository.save(video);

        // ---------- ADD ----------
        playlistService.addVideo(room.getId(), video.getId(), user.getId());

        // ---------- REMOVE ----------
        playlistService.removeVideo(room.getId(), video.getId());

        // ---------- VERIFY ----------
        assertThat(
                playlistRepository.existsByRoomIdAndVideoId(room.getId(), video.getId())
        ).isFalse();
    }
}
