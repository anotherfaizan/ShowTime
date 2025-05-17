package com.ShowTime.ShowTimeApp.repository;

import com.ShowTime.ShowTimeApp.entities.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
