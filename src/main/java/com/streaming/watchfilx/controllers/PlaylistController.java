package com.streaming.watchfilx.controllers;

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
    public ResponseEntity<Playlist> addVideo(
            @PathVariable Long roomId,
            @RequestBody Map<String, Long> body) {

        Long videoId = body.get("videoId");
        Long userId = body.get("userId");

        return ResponseEntity.ok(
                playlistService.addVideo(roomId, videoId, userId)
        );
    }
}
