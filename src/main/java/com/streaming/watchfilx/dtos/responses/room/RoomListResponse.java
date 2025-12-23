package com.streaming.watchfilx.dtos.responses.room;

import java.time.LocalDateTime;

public class RoomListResponse {

    private Long id;
    private String name;
    private String thumbnail;
    private String currentVideo;
    private int viewers;
    private String creator;
    private String description;
    private LocalDateTime createdAt;


    public RoomListResponse(Long id, String name, String thumbnail, String currentVideo, int viewers, String creator, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.currentVideo = currentVideo;
        this.viewers = viewers;
        this.creator = creator;
        this.description = description;
        this.createdAt = createdAt;
    }
}
