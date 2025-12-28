package com.streaming.watchfilx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streaming.watchfilx.dtos.requests.room.CreateRoomRequest;
import com.streaming.watchfilx.dtos.responses.room.RoomListResponse;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.services.RoomService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final ObjectMapper objectMapper;

    public RoomController(RoomService roomService, ObjectMapper objectMapper) {
        this.roomService = roomService;
        this.objectMapper = objectMapper;
    }

    // -----------------------
    //  CRÉER UN SALON (AVEC IMAGE)
    // -----------------------
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RoomListResponse createRoom(
            @RequestPart("data") String roomJson,
            @RequestPart("image") MultipartFile image
    ) throws IOException {

        CreateRoomRequest request =
                objectMapper.readValue(roomJson, CreateRoomRequest.class);

        return roomService.createRoom(request, image);
    }

    // -----------------------
    //  REJOINDRE UN SALON
    // -----------------------
    @PostMapping("/join")
    public String joinRoom(@RequestParam Long roomId, @RequestParam Long userId) {
        return roomService.joinRoom(roomId, userId);
    }

    // -----------------------
    //  QUITTER UN SALON
    // -----------------------
    @PostMapping("/leave")
    public String leaveRoom(@RequestParam Long roomId, @RequestParam Long userId) {
        return roomService.leaveRoom(roomId, userId);
    }

    // -----------------------
    //  PUBLIER UNE VIDÉO
    // -----------------------
    @PostMapping("/publish-video")
    public void publishVideo(@RequestParam Long roomId, @RequestParam Long videoId) {
        roomService.publishVideo(roomId, videoId);
    }

    // -----------------------
    //  LISTE DES SALONS
    // -----------------------
    @GetMapping
    public List<RoomListResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    // -----------------------
    //  LISTE DES MEMBRES D'UN SALON (CRÉATEUR SEULEMENT) ✅ NOUVEAU
    // -----------------------
    @GetMapping("/{roomId}/members")
    public List<User> getRoomMembers(
            @PathVariable Long roomId,
            @RequestParam Long requesterId
    ) {
        return roomService.getRoomMembers(roomId, requesterId);
    }

    // -----------------------
    //  SUPPRIMER UN SALON
    // -----------------------
    @DeleteMapping("/delete")
    public String deleteRoom(@RequestParam Long roomId) {
        return roomService.deleteRoom(roomId);
    }
}
