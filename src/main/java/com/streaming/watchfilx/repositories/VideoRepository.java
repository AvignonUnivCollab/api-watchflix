package com.streaming.watchfilx.repositories;

import com.streaming.watchfilx.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
