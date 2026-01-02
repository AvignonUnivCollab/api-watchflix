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
    private final ObjectMapper objectMapper;

    public MessageController(MessageService messageService, ObjectMapper mapper) {
        this.messageService = messageService;
        this.objectMapper = mapper;
    }

    // ---------------------------
    // ENVOYER UN MESSAGE
    // ---------------------------
    @PostMapping
    public MessageResponse sendMessage(
            @RequestPart("data") String messageJson
    ) throws JsonProcessingException {

        MessageRequest request =
                objectMapper.readValue(messageJson, MessageRequest.class);

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
