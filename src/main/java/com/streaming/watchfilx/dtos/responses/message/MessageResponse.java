package com.streaming.watchfilx.dtos.responses.message;

import com.streaming.watchfilx.dtos.responses.user.UserMiniResponse;

import java.time.LocalDateTime;

public class MessageResponse {

    private Long id;
    private String user;
    private Long roomId;
    private String content;
    private LocalDateTime timestamp;

    public MessageResponse(
            Long id,
            String user,
            Long roomId,
            String content,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.user = user;
        this.roomId = roomId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }

    public String getUser() {
        return user;
    }

    public Long getRoomId() {return roomId;}
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
