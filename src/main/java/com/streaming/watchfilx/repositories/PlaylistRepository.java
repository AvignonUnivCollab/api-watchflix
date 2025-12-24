package com.streaming.watchfilx.repositories;

import com.streaming.watchfilx.models.Playlist;
import com.streaming.watchfilx.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByRoom(Room room);
}
