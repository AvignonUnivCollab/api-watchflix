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

    // -------------------------------------------
    //  CRÉER UNE VIDÉO AVEC VÉRIFICATIONS
    // -------------------------------------------
    public Video createVideo(Video video) {

        // Vérifier si une vidéo avec la même URL existe
        if (videoRepository.findByUrl(video.getUrl()).isPresent()) {
            throw new RuntimeException("Une vidéo avec cette URL existe déjà !");
        }

        // Vérifier si une vidéo avec le même titre existe
        if (videoRepository.findByTitle(video.getTitle()).isPresent()) {
            throw new RuntimeException("Une vidéo avec ce titre existe déjà !");
        }

        return videoRepository.save(video);
    }

    // -------------------------------------------
    //  OBTENIR UNE VIDÉO PAR ID
    // -------------------------------------------
    public Optional<Video> getVideo(Long id) {
        return videoRepository.findById(id);
    }

    // -------------------------------------------
    //  LISTER TOUTES LES VIDÉOS
    // -------------------------------------------
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    // -------------------------------------------
    //  SUPPRIMER UNE VIDÉO PAR ID
    // -------------------------------------------
    public void deleteVideo(Long videoId) {

        // Vérifier que la vidéo existe
        if (!videoRepository.existsById(videoId)) {
            throw new RuntimeException("Vidéo introuvable");
        }

        videoRepository.deleteById(videoId);
    }
}
