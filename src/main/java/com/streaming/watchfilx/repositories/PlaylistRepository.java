package com.streaming.watchfilx.repositories;

import com.streaming.watchfilx.models.Playlist;
import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByRoom(Room room);

    List<Playlist> findAllByRoom(Room room);
    int countByRoom(Room room);

    boolean existsByRoomAndVideo(Room room, Video video);

}
