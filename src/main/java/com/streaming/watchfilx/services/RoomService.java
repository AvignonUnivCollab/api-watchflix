package com.streaming.watchfilx.services;

import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.dtos.responses.message.MessageResponse;
import com.streaming.watchfilx.dtos.responses.playlist.PlaylistVideoResponse;
import com.streaming.watchfilx.dtos.responses.room.RoomDetailResponse;
import com.streaming.watchfilx.dtos.responses.room.RoomListResponse;
import com.streaming.watchfilx.dtos.responses.user.UserMiniResponse;
import com.streaming.watchfilx.dtos.responses.video.VideoResponse;
import com.streaming.watchfilx.models.*;
import com.streaming.watchfilx.repositories.*;
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
    private final PlaylistRepository playlistRepository;
    private final VideoRepository videoRepository;
    private final MessageRepository messageRepository;

    public RoomService(RoomRepository roomRepository,
                       RoomMemberRepository memberRepository,
                       UserRepository userRepository,
                       VideoRepository videoRepository,
                       PlaylistRepository playlistRepository,
                       MessageRepository messageRepository) {

        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.playlistRepository = playlistRepository;
        this.messageRepository = messageRepository;
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

        List<Long> memberIds = List.of();
        //Réponse UI
        return new RoomListResponse(
                room.getId(),
                room.getName(),
                room.getThumbnail(),
                "Aucune vidéo",
                room.getMembers(),
                memberIds,
                creatorName,
                room.getDescription(),
                room.getCreatedAt()
        );

    }

    // -----------------------
    //  AFFICHER UN SALON PAR ID
    // -----------------------
    public RoomDetailResponse getRoomDetail(Long roomId, Long userId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        boolean isMember = memberRepository.existsByRoomIdAndUserId(roomId, userId);

        User owner = userRepository.findById(room.getCreatorId())
                .orElseThrow();

        UserMiniResponse ownerDto =
                new UserMiniResponse(owner.getId(), owner.getPrenom());

        List<VideoResponse> videos = videoRepository.findByRoomId(roomId)
                .stream()
                .map(v -> new VideoResponse(
                        v.getId(),
                        v.getTitle(),
                        v.getUrl(),
                        v.getThumbnail(),
                        v.getDescription(),
                        v.getDuration()
                )).toList();

        List<MessageResponse> messages = messageRepository
                .findByRoomIdOrderByTimestampAsc(roomId)
                .stream()
                .map(m -> {

                    User user = userRepository.findById(m.getUserId())
                            .orElseThrow();

                    String fullName = user.getPrenom() + " " + user.getNom();

                    return new MessageResponse(
                            m.getId(),
                            fullName,
                            room.getId(),
                            m.getContent(),
                            m.getTimestamp()
                    );
                })
                .toList();


        Playlist playlist = playlistRepository.findByRoom(room);

        List<PlaylistVideoResponse> playlistVideos =
                playlist == null ? List.of() :
                        playlist.getVideos().stream()
                                .map(pv -> new PlaylistVideoResponse(
                                        pv.getPosition(),
                                        new VideoResponse(
                                                pv.getVideo().getId(),
                                                pv.getVideo().getTitle(),
                                                pv.getVideo().getUrl(),
                                                pv.getVideo().getThumbnail(),
                                                pv.getVideo().getDescription(),
                                                pv.getVideo().getDuration()
                                        ),
                                        new UserMiniResponse(
                                                pv.getAddedBy().getId(),
                                                pv.getAddedBy().getPrenom()
                                        )
                                )).toList();

        VideoResponse currentVideo = null;
        if (!videos.isEmpty()) {
            currentVideo = videos.get(0);
        }
        return new RoomDetailResponse(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getThumbnail(),
                ownerDto,
                room.getMembers(),
                isMember,
                currentVideo,
                videos,
                playlistVideos,
                messages
        );
    }


    // -----------------------
    //  REJOINDRE UN SALON
    // -----------------------
    public String joinRoom(Long roomId, Long userId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        userRepository.findById(userId)
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
            String creatorName = creator != null
                    ? creator.getNom() + " " + creator.getPrenom()
                    : "Inconnu";

            // Vidéo courante
            Video video = room.getCurrentVideoId() != null
                    ? videoRepository.findById(room.getCurrentVideoId()).orElse(null)
                    : null;

            String videoTitle = video != null ? video.getTitle() : "Aucune vidéo";

            // MEMBRES RÉELS
            List<Long> memberIds = memberRepository.findByRoomId(room.getId())
                    .stream()
                    .map(RoomMember::getUserId)
                    .toList();

            return new RoomListResponse(
                    room.getId(),
                    room.getName(),
                    room.getThumbnail(),
                    videoTitle,
                    room.getMembers(),     // compteur
                    memberIds,             // IDs réels
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

        memberRepository.delete(member);

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

    // -----------------------
    //  LISTE DES MEMBRES (CRÉATEUR SEULEMENT)
    // -----------------------
    public List<User> getRoomMembers(Long roomId, Long requesterId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        if (!room.getCreatorId().equals(requesterId)) {
            throw new RuntimeException("Accès refusé : vous n'êtes pas le créateur du salon");
        }

        List<RoomMember> roomMembers = memberRepository.findByRoomId(roomId);

        return roomMembers.stream()
                .map(member -> userRepository.findById(member.getUserId())
                        .orElseThrow(() -> new RuntimeException("Utilisateur introuvable")))
                .toList();
    }

    // -----------------------
    //  RETIRER UN MEMBRE (CRÉATEUR SEULEMENT) ✅ NOUVEAU
    // -----------------------
    public String removeMember(Long roomId, Long requesterId, Long userIdToRemove) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        // Sécurité : seul le créateur
        if (!room.getCreatorId().equals(requesterId)) {
            throw new RuntimeException("Accès refusé : vous n'êtes pas le créateur du salon");
        }

        RoomMember member = memberRepository.findByRoomIdAndUserId(roomId, userIdToRemove)
                .orElseThrow(() -> new RuntimeException("L'utilisateur n'est pas dans ce salon"));

        memberRepository.delete(member);

        room.setMembers(room.getMembers() - 1);
        roomRepository.save(room);

        return "Utilisateur retiré du salon avec succès";
    }

    
    public String disableInvite(Long roomId, Long requesterId, Long userId) {

    Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Salon introuvable"));

    // Sécurité : seul le créateur
    if (!room.getCreatorId().equals(requesterId)) {
        throw new RuntimeException("Accès refusé : vous n'êtes pas le créateur du salon");
    }

    RoomMember member = memberRepository
            .findByRoomIdAndUserId(roomId, userId)
            .orElseThrow(() -> new RuntimeException("Invitation introuvable"));

    memberRepository.delete(member);

    room.setMembers(room.getMembers() - 1);
    roomRepository.save(room);

    return "Invitation désactivée avec succès";
}

}
