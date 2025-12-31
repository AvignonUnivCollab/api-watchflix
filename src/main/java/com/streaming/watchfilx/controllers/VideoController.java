package com.streaming.watchfilx.controllers;
import com.streaming.watchfilx.dtos.requests.video.UpdateVideoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.dtos.requests.video.CreateVideoRequest;
import com.streaming.watchfilx.dtos.responses.room.RoomListResponse;
import com.streaming.watchfilx.dtos.responses.video.VideoResponse;
import com.streaming.watchfilx.models.Video;
import com.streaming.watchfilx.services.VideoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final ObjectMapper objectMapper;

    public VideoController(VideoService videoService, ObjectMapper objectMapper) {
        this.videoService = videoService;
        this.objectMapper = objectMapper;
    }

    // --------------------------
    //  CRÉER UNE VIDÉO
    // --------------------------
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VideoResponse createVideo(
            @RequestPart("data") String videoJson,
            @RequestPart("image") MultipartFile image
    ) throws IOException {

        CreateVideoRequest request =
                objectMapper.readValue(videoJson, CreateVideoRequest.class);

        return videoService.createVideo(request, image);
    }

    // --------------------------
    //  RÉCUPÉRER UNE VIDÉO PAR ID
    // --------------------------
    @GetMapping("/{id}")
    public Video getVideo(@PathVariable Long id) {
        return videoService.getVideo(id).orElse(null);
    }

    // --------------------------
    //  LISTE DES VIDÉOS
    // --------------------------
    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    // --------------------------
    //   SUPPRIMER UNE VIDÉO
    // --------------------------
    @DeleteMapping("/delete")
    public String deleteVideo(@RequestParam Long videoId) {
        videoService.deleteVideo(videoId);
        return "Vidéo supprimée avec succès";
    }
    // -------------------------------------------
//  RETIRER UNE VIDÉO DU SALON
// -------------------------------------------
@PutMapping("/{videoId}/remove-from-room")
public String removeVideoFromRoom(@PathVariable Long videoId) {
    videoService.removeVideoFromRoom(videoId);
    return "Vidéo retirée du salon avec succès";
}

}
