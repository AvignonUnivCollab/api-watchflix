package com.streaming.watchfilx.dtos.responses.room;

import com.streaming.watchfilx.dtos.responses.user.UserMiniResponse;
import com.streaming.watchfilx.dtos.responses.video.VideoResponse;
import com.streaming.watchfilx.dtos.responses.playlist.PlaylistVideoResponse;
import com.streaming.watchfilx.dtos.responses.message.MessageResponse;

import java.util.List;

public class RoomDetailResponse {

    private Long id;
    private String name;
    private String description;
    private String thumbnail;
    private UserMiniResponse owner;
    private int viewers;
    private boolean isMember;

    private VideoResponse currentVideo;
    private List<VideoResponse> videos;
    private List<PlaylistVideoResponse> playlist;
    private List<MessageResponse> messages;

    public RoomDetailResponse(
            Long id,
            String name,
            String description,
            String thumbnail,
            UserMiniResponse owner,
            int viewers,
            boolean isMember,
            VideoResponse currentVideo,
            List<VideoResponse> videos,
            List<PlaylistVideoResponse> playlist,
            List<MessageResponse> messages
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.owner = owner;
        this.viewers = viewers;
        this.isMember = isMember;
        this.currentVideo = currentVideo;
        this.videos = videos;
        this.playlist = playlist;
        this.messages = messages;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getThumbnail() { return thumbnail; }
    public UserMiniResponse getOwner() { return owner; }
    public int getViewers() { return viewers; }
    public boolean isMember() { return isMember; }
    public VideoResponse getCurrentVideo() { return currentVideo; }
    public List<VideoResponse> getVideos() { return videos; }
    public List<PlaylistVideoResponse> getPlaylist() { return playlist; }
    public List<MessageResponse> getMessages() { return messages; }
}
