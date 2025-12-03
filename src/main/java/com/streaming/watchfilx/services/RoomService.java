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

    public String joinRoom(Long roomId, Long userId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Vérifier si utilisateur déjà membre
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

        // Vérifier que le salon existe
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        // Vérifier que la vidéo existe
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Vidéo introuvable"));

        // Associer la vidéo au salon
        room.setCurrentVideoId(videoId);
        room.setDuration(video.getDuration());

        // Sauvegarde
        return roomRepository.save(room);
    }
}
