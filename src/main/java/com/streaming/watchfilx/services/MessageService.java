package com.streaming.watchfilx.services;

import com.streaming.watchfilx.dtos.requests.message.MessageRequest;
import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.dtos.responses.message.MessageResponse;
import com.streaming.watchfilx.models.Message;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.repositories.MessageRepository;
import com.streaming.watchfilx.repositories.RoomRepository;
import com.streaming.watchfilx.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository,
                          RoomRepository roomRepository,
                          UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    // ---------------------------
    // ENVOYER UN MESSAGE
    // ---------------------------
    public MessageResponse sendMessage(MessageRequest request) {

        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Le message ne peut pas être vide");
        }

        if (!roomRepository.existsById(request.getRoomId())) {
            throw new RuntimeException("Salon introuvable");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Message message = new Message();
        message.setRoomId(request.getRoomId());
        message.setUserId(request.getUserId());
        message.setContent(request.getContent().trim());

        Message result = messageRepository.save(message);

        String fullName = user.getPrenom() + " " + user.getNom();

        return new MessageResponse(
                result.getId(),
                fullName,
                result.getRoomId(),
                result.getContent(),
                result.getTimestamp()
        );
    }

    // ---------------------------
    // RÉCUPÉRER LES MESSAGES D’UN SALON
    // ---------------------------
    public List<Message> getRoomMessages(Long roomId) {

        if (!roomRepository.existsById(roomId)) {
            throw new RuntimeException("Salon introuvable");
        }

        return messageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }
}
