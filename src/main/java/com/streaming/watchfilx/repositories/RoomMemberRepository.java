package com.streaming.watchfilx.repositories;

import com.streaming.watchfilx.models.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    Optional<RoomMember> findByRoomIdAndUserId(Long roomId, Long userId);
}
