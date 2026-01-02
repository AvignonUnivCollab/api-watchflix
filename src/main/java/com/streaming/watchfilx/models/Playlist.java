package com.streaming.watchfilx.models;

import jakarta.persistence.*;

@Entity
@Table(
        name = "playlist",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "video_id"})
)
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int position;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // La vidéo associée (déjà liée au salon)
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    // L'utilisateur qui a ajouté la vidéo
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addedBy;

    public Playlist() {}

    public Playlist(Video video, Room room, User addedBy, int position) {
        this.video = video;
        this.room = room;
        this.addedBy = addedBy;
        this.position = position;
    }

    public Long getId() { return id; }
    public int getPosition() { return position; }
    public Video getVideo() { return video; }
    public User getAddedBy() { return addedBy; }

    public Room getRoom() {
        return room;
    }

    public void setPosition(int position) { this.position = position; }
}
