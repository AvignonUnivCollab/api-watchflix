package com.streaming.watchfilx.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Une playlist appartient à un seul salon
    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<PlaylistVideo> videos = new ArrayList<>();

    public Playlist() {}

    public Playlist(Room room) {
        this.room = room;
    }

    public Long getId() { return id; }
    public Room getRoom() { return room; }
    public List<PlaylistVideo> getVideos() { return videos; }

    public void setRoom(Room room) { this.room = room; }
}
