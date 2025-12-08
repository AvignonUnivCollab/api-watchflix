package com.streaming.watchfilx.services;

import com.streaming.watchfilx.models.*;
import com.streaming.watchfilx.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepo;
    private final PlaylistVideoRepository playlistVideoRepo;
    private final RoomRepository roomRepo;
    private final VideoRepository videoRepo;
    private final UserRepository userRepo;

    public PlaylistService(PlaylistRepository playlistRepo,
                           PlaylistVideoRepository playlistVideoRepo,
                           RoomRepository roomRepo,
                           VideoRepository videoRepo,
                           UserRepository userRepo) {
        this.playlistRepo = playlistRepo;
        this.playlistVideoRepo = playlistVideoRepo;
        this.roomRepo = roomRepo;
        this.videoRepo = videoRepo;
        this.userRepo = userRepo;
    }

    public Playlist addVideo(Long roomId, Long videoId, Long userId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Playlist playlist = playlistRepo.findByRoom(room);
        if (playlist == null) {
            playlist = new Playlist(room);
            playlistRepo.save(playlist);
        }

        Video video = videoRepo.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int nextPosition = playlist.getVideos().size() + 1;

        PlaylistVideo playlistVideo = new PlaylistVideo(video, playlist, user, nextPosition);

        playlistVideoRepo.save(playlistVideo);

        return playlist;
    }
}
