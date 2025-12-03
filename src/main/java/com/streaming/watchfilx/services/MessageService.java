package com.streaming.watchfilx.services;

import com.streaming.watchfilx.models.Message;
import com.streaming.watchfilx.models.Room;
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
    public Message sendMessage(Long roomId, Long userId, String content) {

        // Vérifier que la salle existe
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        // Vérifier que l'utilisateur existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Créer le message
        Message message = new Message();
        message.setRoomId(roomId);
        message.setUserId(userId);
        message.setContent(content);

        return messageRepository.save(message);
    }

    // ---------------------------
    // RÉCUPÉRER LES MESSAGES D’UN SALON
    // ---------------------------
    public List<Message> getRoomMessages(Long roomId) {

        // Vérifier que le salon existe
        roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Salon introuvable"));

        return messageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }
}
