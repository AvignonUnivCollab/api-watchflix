package com.streaming.watchfilx.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom du salon (obligatoire)
    @Column(nullable = false, unique = true)
    private String name;

    // Miniature optionnelle
    private String thumbnail;

    // Peut être NULL → éviter les erreurs SQL si la vidéo n’existe pas
    @Column(name = "current_video_id", nullable = true)
    private Long currentVideoId;

    // Nombre de membres (initialisé à 0)
    private Integer members;

    // Durée en secondes (optionnelle)
    private Integer duration;

    // ID du créateur (obligatoire)
    @Column(nullable = false)
    private Long creatorId;

    // Description optionnelle
    @Column(columnDefinition = "TEXT")
    private String description;

    // Date de création automatique
    private LocalDateTime createdAt;

    public Room() {
        this.createdAt = LocalDateTime.now();
        this.members = 0;
    }

    // -------------------
    // GETTERS & SETTERS
    // -------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public Long getCurrentVideoId() { return currentVideoId; }
    public void setCurrentVideoId(Long currentVideoId) { this.currentVideoId = currentVideoId; }

    public Integer getMembers() { return members; }
    public void setMembers(Integer members) { this.members = members; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
