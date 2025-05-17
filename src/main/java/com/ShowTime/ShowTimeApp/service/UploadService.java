package com.ShowTime.ShowTimeApp.service;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.modal.VideoDto;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface UploadService {

    Video uploadVideo(VideoDto videoDto, MultipartFile file);

    void processVideo(String videoId, Path videoFilePath);
}
