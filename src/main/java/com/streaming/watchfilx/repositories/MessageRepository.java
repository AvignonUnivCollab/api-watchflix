package com.streaming.watchfilx.repositories;

import com.streaming.watchfilx.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Récupérer tous les messages d'un salon
    List<Message> findByRoomIdOrderByTimestampAsc(Long roomId);

    // (Optionnel) Récupérer les messages d'un user dans un salon
    List<Message> findByRoomIdAndUserId(Long roomId, Long userId);
}
