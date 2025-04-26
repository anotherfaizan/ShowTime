package com.ShowTime.ShowTimeApp.service.impl;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.mapper.VideoMapper;
import com.ShowTime.ShowTimeApp.modal.VideoDto;
import com.ShowTime.ShowTimeApp.repository.UploadDAO;
import com.ShowTime.ShowTimeApp.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadDAO uploadDAO;

    @Override
    public String uploadVideo(VideoDto videoDto) {
        Video video = VideoMapper.convertVideoDtoToVideo(videoDto);
        uploadDAO.save(video);
        return "Okay";
    }
}
