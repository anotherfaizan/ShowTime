package com.ShowTime.ShowTimeApp.service.impl;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.repository.VideoRepository;
import com.ShowTime.ShowTimeApp.service.VideoStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoStreamServiceImpl implements VideoStreamService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Video getVideo(String videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(()
                -> new RuntimeException("Video with the given id doesn't exist")
        );
        return video;
    }
}
