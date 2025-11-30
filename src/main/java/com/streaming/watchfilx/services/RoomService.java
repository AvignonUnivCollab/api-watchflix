package com.streaming.watchfilx.services;

import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.RoomMember;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.repositories.RoomMemberRepository;
import com.streaming.watchfilx.repositories.RoomRepository;
import com.streaming.watchfilx.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository memberRepository;
    private final UserRepository userRepository;

    public RoomService(RoomRepository roomRepository,
                       RoomMemberRepository memberRepository,
                       UserRepository userRepository) {

        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    // -----------------------
    //  CRÉATION DE SALON
    // -----------------------
    public Room createRoom(Room room) {

        // Vérifier si un salon avec le même nom existe déjà
        if (roomRepository.findByName(room.getName()).isPresent()) {
            throw new RuntimeException("Un salon avec ce nom existe déjà !");
        }

        // Initialiser members si non fourni
        if (room.getMembers() == null) {
            room.setMembers(0);
        }

        // Vérifier currentVideoId : si <= 0 -> mettre null
        if (room.getCurrentVideoId() != null && room.getCurrentVideoId() <= 0) {
            room.setCurrentVideoId(null);
        }

        return roomRepository.save(room);
    }

    // -----------------------
    //  REJOINDRE UN SALON
    // -----------------------
    public String joinRoom(Long roomId, Long userId) {

        // Vérifier que le salon existe
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        // Vérifier que l'utilisateur existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Vérifier si utilisateur est déjà dans le salon
        boolean alreadyMember = memberRepository.findByRoomIdAndUserId(roomId, userId).isPresent();
        if (alreadyMember) {
            return "Utilisateur déjà dans le salon";
        }

        // Ajouter l'utilisateur au salon
        RoomMember member = new RoomMember();
        member.setRoomId(roomId);
        member.setUserId(userId);
        memberRepository.save(member);

        // Incrémenter le nombre de membres
        room.setMembers(room.getMembers() + 1);
        roomRepository.save(room);

        return "Utilisateur ajouté au salon avec succès";
    }

    // -----------------------
    //  PUBLIER UNE VIDÉO DANS UN SALON
    // -----------------------
    public Room publishVideo(Long roomId, Long videoId) {

        // Vérifier que le salon existe
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        // Vérifier que l'ID vidéo est valide
        if (videoId == null || videoId <= 0) {
            throw new RuntimeException("ID vidéo invalide");
        }

        // Associer la vidéo au salon
        room.setCurrentVideoId(videoId);

        // Sauvegarder
        return roomRepository.save(room);
    }
}
