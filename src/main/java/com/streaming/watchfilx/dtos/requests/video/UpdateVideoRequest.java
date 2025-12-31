package com.streaming.watchfilx.dtos.requests.video;

public class UpdateVideoRequest {

    private String title;
    private String description;
    private String url;
    private String thumbnail;

    public UpdateVideoRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
}

