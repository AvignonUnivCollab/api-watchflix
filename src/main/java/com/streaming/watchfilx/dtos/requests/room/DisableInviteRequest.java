package com.streaming.watchfilx.dtos.requests.room;

public class DisableInviteRequest {

    private Long roomId;
    private Long requesterId;
    private Long userId;

    public Long getRoomId() {
        return roomId;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public Long getUserId() {
        return userId;
    }
}
