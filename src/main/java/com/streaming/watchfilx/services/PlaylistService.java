package com.streaming.watchfilx.services;

import com.streaming.watchfilx.dtos.responses.playlist.PlaylistVideoResponse;
import com.streaming.watchfilx.dtos.responses.user.UserMiniResponse;
import com.streaming.watchfilx.dtos.responses.video.VideoResponse;
import com.streaming.watchfilx.models.*;
import com.streaming.watchfilx.repositories.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepo;
    private final RoomRepository roomRepo;
    private final VideoRepository videoRepo;
    private final UserRepository userRepo;

    public PlaylistService(PlaylistRepository playlistRepo,
                           RoomRepository roomRepo,
                           VideoRepository videoRepo,
                           UserRepository userRepo) {
        this.playlistRepo = playlistRepo;
        this.roomRepo = roomRepo;
        this.videoRepo = videoRepo;
        this.userRepo = userRepo;
    }

    public PlaylistVideoResponse addVideo(Long roomId, Long videoId, Long userId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Video video = videoRepo.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (playlistRepo.existsByRoomAndVideo(room, video)) {
            throw new RuntimeException("Cette vidéo est déjà dans la playlist");
        }

        int nextPosition = playlistRepo
                .findByRoomIdOrderByPositionAsc(roomId)
                .size() + 1;
        Playlist result = playlistRepo.save(new Playlist(video, room, user, nextPosition));

        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setId(result.getVideo().getId());
        videoResponse.setUrl(result.getVideo().getUrl());
        videoResponse.setDescription(result.getVideo().getDescription());
        videoResponse.setThumbnail(result.getVideo().getThumbnail());

        UserMiniResponse userMiniResponse = new UserMiniResponse();
        userMiniResponse.setId(user.getId());
        userMiniResponse.setName(user.getNom() + " " + user.getPrenom());

        return new PlaylistVideoResponse(result.getPosition(), videoResponse, userMiniResponse);
    }

    @Transactional
    public void removeVideo(Long roomId, Long videoId) {

        if (!playlistRepo.existsByRoomIdAndVideoId(roomId, videoId)) {
            throw new RuntimeException("Cette vidéo n'est pas dans la playlist");
        }

        playlistRepo.deleteByRoomIdAndVideoId(roomId, videoId);

        //Recalculer les positions après suppression
        List<Playlist> remaining = playlistRepo.findByRoomIdOrderByPositionAsc(roomId);

        int position = 1;
        for (Playlist p : remaining) {
            p.setPosition(position++);
        }

        playlistRepo.saveAll(remaining);
    }
}
