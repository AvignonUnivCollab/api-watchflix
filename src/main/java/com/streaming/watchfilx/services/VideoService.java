package com.streaming.watchfilx.services;

import com.streaming.watchfilx.models.Video;
import com.streaming.watchfilx.repositories.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    public Optional<Video> getVideo(Long id) {
        return videoRepository.findById(id);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
