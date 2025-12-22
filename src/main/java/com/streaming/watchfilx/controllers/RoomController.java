package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.models.User;
import com.streaming.watchfilx.services.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // -----------------------
    //  CRÉER UN SALON
    // -----------------------
    @PostMapping("/create")
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    // -----------------------
    //  REJOINDRE UN SALON
    // -----------------------
    @PostMapping("/join")
    public String joinRoom(@RequestParam Long roomId, @RequestParam Long userId) {
        return roomService.joinRoom(roomId, userId);
    }

    // -----------------------
    //  PUBLIER UNE VIDÉO
    // -----------------------
    @PostMapping("/publish-video")
    public Room publishVideo(@RequestParam Long roomId, @RequestParam Long videoId) {
        return roomService.publishVideo(roomId, videoId);
    }

    // -----------------------
    //  LISTE DES SALONS
    // -----------------------
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    // -----------------------
    //  AFFICHER UN SALON PAR ID
    // -----------------------
    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    // -----------------------
    //  QUITTER UN SALON
    // -----------------------
    @PostMapping("/leave")
    public String leaveRoom(@RequestParam Long roomId, @RequestParam Long userId) {
        return roomService.leaveRoom(roomId, userId);
    }

    // -----------------------
    //  SUPPRIMER UN SALON
    // -----------------------
    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable Long roomId) {
        return roomService.deleteRoom(roomId);
    }

    // -----------------------
    //  LISTE DES MEMBRES DU SALON (CRÉATEUR SEULEMENT)
    // -----------------------
    @GetMapping("/{roomId}/members")
    public List<User> getRoomMembers(
            @PathVariable Long roomId,
            @RequestParam Long requesterId
    ) {
        return roomService.getRoomMembers(roomId, requesterId);
    }
}
