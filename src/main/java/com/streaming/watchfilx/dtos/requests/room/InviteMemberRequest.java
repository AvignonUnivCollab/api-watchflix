package com.streaming.watchfilx.dtos.requests.room;

public class InviteMemberRequest {

    private Long roomId;
    private Long requesterId; // le créateur
    private Long userId;      // invité

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
