package com.ShowTime.ShowTimeApp.service.impl;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.mapper.VideoMapper;
import com.ShowTime.ShowTimeApp.modal.VideoDto;
import com.ShowTime.ShowTimeApp.repository.VideoRepository;
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

    @Value("${files.video.hls}")
    private String HLS_DIR;

    private final VideoRepository videoRepository;

    public UploadServiceImpl(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    @PostConstruct
    public void init(){
        File file = new File(DIR);

        // creating folder if it doesn't exist already
        if(!file.exists()){
            file.mkdir();
        }

        try {
            Files.createDirectories(Paths.get(HLS_DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
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

            Video savedVideo = videoRepository.save(video);
            
            // processing the video for HLS
            processVideo(savedVideo.getId(), path);
            
            return savedVideo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processVideo(String videoId, Path videoFilePath) {
        try {
            Path outputPath = Paths.get(HLS_DIR, videoId);
            Files.createDirectories(outputPath);

            // ffmpeg command to encode
            String ffmpegCmd = String.format(
                    "ffmpeg -i \"%s\" " +
                            "-c:v libx264 -profile:v main -preset veryfast -g 50 -keyint_min 50 -sc_threshold 0 " +
                            "-c:a aac -b:a 128k " +
                            "-hls_time 10 -hls_list_size 0 -hls_segment_type mpegts " +
                            "-hls_flags independent_segments " +
                            "-hls_segment_filename \"%s/segment_%%03d.ts\" \"%s/master.m3u8\"",
                    videoFilePath, outputPath, outputPath
            );

            // Get the operating system name from the system properties
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            // Launching a bash/cmd shell to execute the ffmpegCmd string command
            if (os.contains("win")) {
                processBuilder = new ProcessBuilder("cmd.exe", "/c", ffmpegCmd);
            } else {
                // On Unix/Linux/macOS
                processBuilder = new ProcessBuilder("/bin/bash", "-c", ffmpegCmd);
            }

            processBuilder.inheritIO();
            Process process = processBuilder.start();

            // Waiting for the process to complete and capturing the exit code
            int exit = process.waitFor();
            if (exit != 0) {
                throw new RuntimeException("Video processing failed!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Video Processing failed!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
