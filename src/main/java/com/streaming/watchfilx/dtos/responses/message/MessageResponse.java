package com.streaming.watchfilx.dtos.responses.message;

import java.time.LocalDateTime;

public class MessageResponse {

    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime timestamp;

    public MessageResponse(
            Long id,
            Long userId,
            String content,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
