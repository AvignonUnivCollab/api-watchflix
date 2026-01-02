package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.dtos.requests.message.MessageRequest;
import com.streaming.watchfilx.dtos.requests.playlist.PlaylistRequest;
import com.streaming.watchfilx.dtos.responses.playlist.PlaylistVideoResponse;
import com.streaming.watchfilx.models.Message;
import com.streaming.watchfilx.models.Playlist;
import com.streaming.watchfilx.services.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rooms/{roomId}/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/add-video")
    public PlaylistVideoResponse addVideo(
            @PathVariable Long roomId,
            @RequestBody PlaylistRequest request
    ) {
        return playlistService.addVideo(
                roomId,
                request.getVideoId(),
                request.getUserId()
        );
    }

    @DeleteMapping("/remove-video/{videoId}")
    public ResponseEntity<?> removeVideo(
            @PathVariable Long roomId,
            @PathVariable Long videoId
    ) {
        playlistService.removeVideo(roomId, videoId);
        return ResponseEntity.ok().build();
    }
}

