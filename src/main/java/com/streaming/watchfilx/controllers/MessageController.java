package com.streaming.watchfilx.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streaming.watchfilx.dtos.requests.message.MessageRequest;
import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.dtos.responses.message.MessageResponse;
import com.streaming.watchfilx.models.Message;
import com.streaming.watchfilx.models.Video;
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
    public MessageResponse sendMessage(@RequestBody Message message) {

        MessageRequest request = new MessageRequest();
        request.setUserId(message.getUserId());
        request.setContent(message.getContent());
        request.setRoomId(message.getRoomId());
        return messageService.sendMessage(request);
    }

    // ---------------------------
    // RÉCUPÉRER LES MESSAGES D’UN SALON
    // ---------------------------
    @GetMapping("/room/{roomId}")
    public List<Message> getRoomMessages(@PathVariable Long roomId) {
        return messageService.getRoomMessages(roomId);
    }
}
