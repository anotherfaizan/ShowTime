package com.ShowTime.ShowTimeApp.service;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.modal.VideoDto;

public interface UploadService {
    public String uploadVideo(VideoDto videoDto);
}
