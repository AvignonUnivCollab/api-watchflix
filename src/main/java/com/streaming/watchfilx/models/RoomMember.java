package com.streaming.watchfilx.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_member")
public class RoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;
    private Long userId;

    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;

    public RoomMember() {
        this.joinedAt = LocalDateTime.now();
    }

    // GETTERS & SETTERS
    public Long getId() { return id; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }

    public LocalDateTime getLeftAt() { return leftAt; }
    public void setLeftAt(LocalDateTime leftAt) { this.leftAt = leftAt; }
}
