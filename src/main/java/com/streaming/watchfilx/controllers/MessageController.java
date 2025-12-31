package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.models.Message;
import com.streaming.watchfilx.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // ---------------------------
    // ENVOYER UN MESSAGE
    // ---------------------------
    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestParam Long roomId,
            @RequestParam Long userId,
            @RequestParam String content
    ) {
        try {
            Message message = messageService.sendMessage(roomId, userId, content);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ---------------------------
    // RÉCUPÉRER LES MESSAGES D’UN SALON
    // ---------------------------
    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getRoomMessages(@PathVariable Long roomId) {
        try {
            List<Message> messages = messageService.getRoomMessages(roomId);
            return ResponseEntity.ok(messages);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
