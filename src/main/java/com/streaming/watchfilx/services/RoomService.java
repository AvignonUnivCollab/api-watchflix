package com.streaming.watchfilx.services;

import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.dtos.responses.room.RoomListResponse;
import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.RoomMember;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.models.Video;
import com.streaming.watchfilx.repositories.RoomMemberRepository;
import com.streaming.watchfilx.repositories.RoomRepository;
import com.streaming.watchfilx.repositories.UserRepository;
import com.streaming.watchfilx.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository memberRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public RoomService(RoomRepository roomRepository,
                       RoomMemberRepository memberRepository,
                       UserRepository userRepository,
                       VideoRepository videoRepository) {

        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    // -----------------------
    //  CRÉER UN SALON
    // -----------------------
    public RoomListResponse createRoom(CreateRoomRequest request, MultipartFile image) {

        //Validation image
        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Une image est obligatoire");
        }

        if (image.getContentType() == null || !image.getContentType().startsWith("image/")) {
            throw new RuntimeException("Le fichier doit être une image");
        }

        //Vérifier unicité du nom
        if (roomRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Un salon avec ce nom existe déjà !");
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
        Room room = new Room();
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setCreatorId(request.getCreatorId());
        room.setThumbnail(imageUrl);
        room.setMembers(0);
        room.setCreatedAt(LocalDateTime.now());

        roomRepository.save(room);

        //Créateur
        User creator = userRepository.findById(room.getCreatorId()).orElse(null);
        String creatorName = creator != null
                ? creator.getNom() + " " + creator.getPrenom()
                : "Inconnu";

        //Réponse UI
        return new RoomListResponse(
                room.getId(),
                room.getName(),
                room.getThumbnail(),
                "Aucune vidéo",
                room.getMembers(),
                creatorName,
                room.getDescription(),
                room.getCreatedAt()
        );
    }

    // -----------------------
    //  REJOINDRE UN SALON
    // -----------------------
    public String joinRoom(Long roomId, Long userId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        boolean alreadyMember = memberRepository.findByRoomIdAndUserId(roomId, userId).isPresent();
        if (alreadyMember) {
            return "Utilisateur déjà dans le salon";
        }

        RoomMember member = new RoomMember();
        member.setRoomId(roomId);
        member.setUserId(userId);
        memberRepository.save(member);

        room.setMembers(room.getMembers() + 1);
        roomRepository.save(room);

        return "Utilisateur ajouté au salon avec succès";
    }

    // -----------------------
    //  PUBLIER UNE VIDÉO
    // -----------------------
    public Room publishVideo(Long roomId, Long videoId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Vidéo introuvable"));

        room.setCurrentVideoId(videoId);
        room.setDuration(video.getDuration());

        return roomRepository.save(room);
    }

    // -----------------------
    //  LISTE DES SALONS
    // -----------------------
    public List<RoomListResponse> getAllRooms() {

        return roomRepository.findAll().stream().map(room -> {

            // Créateur
            User creator = userRepository.findById(room.getCreatorId()).orElse(null);

            String creatorName = creator != null ? creator.getNom() + " " + creator.getPrenom(): "Inconnu";

            // Vidéo courante
            Video video = room.getCurrentVideoId() != null
                    ? videoRepository.findById(room.getCurrentVideoId()).orElse(null)
                    : null;

            String videoTitle = video != null ? video.getTitle() : "Aucune vidéo";

            return new RoomListResponse(
                    room.getId(),
                    room.getName(),
                    room.getThumbnail(),
                    videoTitle,
                    room.getMembers(),
                    creatorName,
                    room.getDescription(),
                    room.getCreatedAt()
            );

        }).collect(Collectors.toList());
    }

    // -----------------------
    //  QUITTER UN SALON
    // -----------------------
    public String leaveRoom(Long roomId, Long userId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        RoomMember member = memberRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new RuntimeException("L'utilisateur n'est pas dans ce salon"));

        // Supprimer le membre
        memberRepository.delete(member);

        // Mettre à jour le nombre de membres
        room.setMembers(room.getMembers() - 1);
        roomRepository.save(room);

        return "Utilisateur a quitté le salon avec succès";
    }

    // -----------------------
    //  SUPPRIMER UN SALON
    // -----------------------
    public String deleteRoom(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        List<RoomMember> members = memberRepository.findByRoomId(roomId);
        memberRepository.deleteAll(members);

        roomRepository.delete(room);

        return "Salon supprimé avec succès";
    }
}
