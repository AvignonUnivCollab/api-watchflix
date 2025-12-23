package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

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
    //  SUPPRIMER UN SALON
    // -----------------------
    @DeleteMapping("/delete")
    public String deleteRoom(@RequestParam Long roomId) {
        return roomService.deleteRoom(roomId);
    }
}
