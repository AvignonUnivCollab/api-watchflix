package com.streaming.watchfilx.services;

import com.streaming.watchfilx.models.Message;
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
    public Message sendMessage(Long roomId, Long userId, String content) {

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Le message ne peut pas être vide");
        }

        // Vérifier que la salle existe
        if (!roomRepository.existsById(roomId)) {
            throw new RuntimeException("Salon introuvable");
        }

        // Vérifier que l'utilisateur existe
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        Message message = new Message();
        message.setRoomId(roomId);
        message.setUserId(userId);
        message.setContent(content.trim());

        return messageRepository.save(message);
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
