package com.streaming.watchfilx.dtos.responses.room;

public class RoomListResponse {

    private Long id;
    private String name;
    private String thumbnail;
    private String currentVideo;
    private int viewers;
    private String duration;
    private String creator;
    private String description;
    private String createdAt;


    public RoomListResponse(Long id, String name, String thumbnail, String currentVideo, int viewers, Integer duration, String creator, String description, String createdAt) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.currentVideo = currentVideo;
        this.viewers = viewers;
        this.duration = duration;
        this.creator = creator;
        this.description = description;
        this.createdAt = createdAt;
    }
}
