package com.ShowTime.ShowTimeApp.service;

import com.ShowTime.ShowTimeApp.entities.Video;

public interface VideoStreamService {
    Video getVideo(String videoId);
}
