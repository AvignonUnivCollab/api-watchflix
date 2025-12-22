package com.streaming.watchfilx.services;

import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.RoomMember;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.models.Video;
import com.streaming.watchfilx.repositories.RoomMemberRepository;
import com.streaming.watchfilx.repositories.RoomRepository;
import com.streaming.watchfilx.repositories.UserRepository;
import com.streaming.watchfilx.repositories.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Room createRoom(Room room) {

        if (roomRepository.findByName(room.getName()).isPresent()) {
            throw new RuntimeException("Un salon avec ce nom existe déjà !");
        }

        if (room.getMembers() == null) {
            room.setMembers(0);
        }

        if (room.getCurrentVideoId() != null && room.getCurrentVideoId() <= 0) {
            room.setCurrentVideoId(null);
        }

        return roomRepository.save(room);
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
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // -----------------------
    //  AFFICHER UN SALON PAR ID
    // -----------------------
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));
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

        //  Sécurité : seul le créateur peut voir les membres
        if (!room.getCreatorId().equals(requesterId)) {
            throw new RuntimeException("Accès refusé : vous n'êtes pas le créateur du salon");
        }

        List<RoomMember> roomMembers = memberRepository.findByRoomId(roomId);

        return roomMembers.stream()
                .map(member -> userRepository.findById(member.getUserId())
                        .orElseThrow(() -> new RuntimeException("Utilisateur introuvable")))
                .toList();
    }
}
