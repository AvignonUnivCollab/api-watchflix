package com.streaming.watchfilx.repositories;

import com.streaming.watchfilx.models.Playlist;
import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.Video;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByRoomId(Long roomId);

    List<Playlist> findAllByRoom(Room room);
    int countByRoom(Room room);

    boolean existsByRoomAndVideo(Room room, Video video);
    boolean existsByRoomIdAndVideoId(Long roomId, Long videoId);

    List<Playlist> findByRoomIdOrderByPositionAsc(Long roomId);

    @Modifying
    @Transactional
    void deleteByRoomIdAndVideoId(Long roomId, Long videoId);
}
