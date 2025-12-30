package com.streaming.watchfilx.services;

import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.dtos.requests.video.CreateVideoRequest;
import com.streaming.watchfilx.dtos.responses.room.RoomListResponse;
import com.streaming.watchfilx.dtos.responses.video.VideoResponse;
import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.models.Video;
import com.streaming.watchfilx.repositories.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    // -------------------------------------------
    //  CRÉER UNE VIDÉO AVEC VÉRIFICATIONS
    // -------------------------------------------
    public VideoResponse createVideo(CreateVideoRequest request, MultipartFile image) {

        //Validation image
        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Une image est obligatoire");
        }

        if (image.getContentType() == null || !image.getContentType().startsWith("image/")) {
            throw new RuntimeException("Le fichier doit être une image");
        }

        //Vérifier unicité du nom
        if (videoRepository.findByTitle(request.getName()).isPresent()) {
            throw new RuntimeException("Une video avec ce nom existe déjà !");
        }

        String imageUrl;
        try {
            Files.createDirectories(Paths.get("uploads"));

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.write(path, image.getBytes());
            imageUrl = "/images/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image");
        }

        //Création du salon
        Video video = new Video();
        video.setTitle(request.getName());
        video.setDescription(request.getDescription());
        video.setUserId(request.getCreatorId());
        video.setThumbnail(imageUrl);
        video.setUrl(request.getUrl());
        video.setRoomId(request.getRoomId());

        videoRepository.save(video);

        return new VideoResponse(video.getId(), video.getTitle(), video.getUrl(), video.getThumbnail(), video.getDescription(), 1);

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
    // -------------------------------------------
//  RETIRER UNE VIDÉO DU SALON
// -------------------------------------------
public void removeVideoFromRoom(Long videoId) {

    Video video = videoRepository.findById(videoId)
            .orElseThrow(() -> new RuntimeException("Vidéo introuvable"));

    video.setRoomId(null);
    videoRepository.save(video);
}
}
