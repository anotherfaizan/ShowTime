package com.ShowTime.ShowTimeApp.service.impl;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.mapper.VideoMapper;
import com.ShowTime.ShowTimeApp.modal.VideoDto;
import com.ShowTime.ShowTimeApp.repository.UploadDAO;
import com.ShowTime.ShowTimeApp.service.UploadService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${files.video}")
    private String DIR;

    private UploadDAO uploadDAO;

    public UploadServiceImpl(UploadDAO uploadDAO){
        this.uploadDAO = uploadDAO;
    }

    @PostConstruct
    public void init(){
        File file = new File(DIR);

        // creating folder if it doesn't exist already
        if(!file.exists()){
            file.mkdir();
        }
    }

    @Override
    public Video uploadVideo(VideoDto videoDto, MultipartFile file) {
        Video video = VideoMapper.convertVideoDtoToVideo(videoDto);

        try {
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            String cleanFileName = StringUtils.cleanPath(filename);
            String cleanFolder = StringUtils.cleanPath(DIR);

            // creating the full path
            Path path = Paths.get(cleanFolder, cleanFileName);

            // copying the file to the folder
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            video.setContentType(contentType);
            video.setFilePath(path.toString());

            return uploadDAO.save(video);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
