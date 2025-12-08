package com.streaming.watchfilx.models;

import jakarta.persistence.*;

@Entity
public class PlaylistVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int position;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    // La vidéo associée (déjà liée au salon)
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    // L'utilisateur qui a ajouté la vidéo
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addedBy;

    public PlaylistVideo() {}

    public PlaylistVideo(Video video, Playlist playlist, User addedBy, int position) {
        this.video = video;
        this.playlist = playlist;
        this.addedBy = addedBy;
        this.position = position;
    }

    public Long getId() { return id; }
    public int getPosition() { return position; }
    public Video getVideo() { return video; }
    public User getAddedBy() { return addedBy; }

    public void setPosition(int position) { this.position = position; }
}
