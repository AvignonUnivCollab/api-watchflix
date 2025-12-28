package com.streaming.watchfilx.dtos.responses.playlist;

import com.streaming.watchfilx.dtos.responses.user.UserMiniResponse;
import com.streaming.watchfilx.dtos.responses.video.VideoResponse;

public class PlaylistVideoResponse {

    private int position;
    private VideoResponse video;
    private UserMiniResponse addedBy;

    public PlaylistVideoResponse(
            int position,
            VideoResponse video,
            UserMiniResponse addedBy
    ) {
        this.position = position;
        this.video = video;
        this.addedBy = addedBy;
    }

    public int getPosition() { return position; }
    public VideoResponse getVideo() { return video; }
    public UserMiniResponse getAddedBy() { return addedBy; }
}
