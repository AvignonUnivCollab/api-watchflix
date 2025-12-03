package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.models.Video;
import com.streaming.watchfilx.services.VideoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/create")
    public Video createVideo(@RequestBody Video video) {
        return videoService.createVideo(video);
    }

    @GetMapping("/{id}")
    public Video getVideo(@PathVariable Long id) {
        return videoService.getVideo(id).orElse(null);
    }

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }
}
