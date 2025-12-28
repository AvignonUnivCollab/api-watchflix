package com.streaming.watchfilx.dtos.responses.room;

import com.streaming.watchfilx.models.User;

import java.time.LocalDateTime;
import java.util.List;

public class RoomListResponse {

    private Long id;
    private String name;
    private String thumbnail;
    private String currentVideo;
    private int viewers;
    private List<Long> memberIds;
    private String creator;
    private String description;
    private LocalDateTime createdAt;

    public RoomListResponse() {
    }

    public RoomListResponse(Long id,
                            String name,
                            String thumbnail,
                            String currentVideo,
                            int viewers,
                            List<Long> membersIds,
                            String creator,
                            String description,
                            LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.currentVideo = currentVideo;
        this.viewers = viewers;
        this.creator = creator;
        this.memberIds = membersIds;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getThumbnail() { return thumbnail; }
    public String getCurrentVideo() { return currentVideo; }
    public int getViewers() { return viewers; }
    public String getCreator() { return creator; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public List<Long> getMemberIds() {
        return memberIds;
    }
}
