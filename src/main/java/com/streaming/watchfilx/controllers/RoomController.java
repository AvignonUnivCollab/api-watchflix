package com.streaming.watchfilx.controllers;

import com.streaming.watchfilx.models.Room;
import com.streaming.watchfilx.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
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
    //  PUBLIER UNE VIDEO DANS UN SALON
    // -----------------------
    @PostMapping("/publish-video")
    public Room publishVideo(@RequestParam Long roomId, @RequestParam Long videoId) {
        return roomService.publishVideo(roomId, videoId);
    }
}
