package com.ShowTime.ShowTimeApp.service;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.modal.VideoDto;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    Video uploadVideo(VideoDto videoDto, MultipartFile file);
}
