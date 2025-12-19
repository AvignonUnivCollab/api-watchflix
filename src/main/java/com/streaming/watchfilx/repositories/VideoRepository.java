package com.streaming.watchfilx.repositories;

import com.streaming.watchfilx.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByUrl(String url);

    Optional<Video> findByTitle(String title);
}
