package com.streaming.watchfilx.dtos.responses.video;

public class VideoResponse {

    private Long id;
    private String title;
    private String url;
    private String thumbnail;
    private String description;
    private Integer duration;

    public VideoResponse() {

    }
    public VideoResponse(
            Long id,
            String title,
            String url,
            String thumbnail,
            String description,
            Integer duration
    ) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnail = thumbnail;
        this.description = description;
        this.duration = duration;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getThumbnail() { return thumbnail; }
    public String getDescription() { return description; }
    public Integer getDuration() { return duration; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
