package com.ShowTime.ShowTimeApp.controller;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.mapper.VideoMapper;
import com.ShowTime.ShowTimeApp.modal.VideoDto;
import com.ShowTime.ShowTimeApp.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@CrossOrigin(
    origins = "${allowCrossOrigin}",
    allowCredentials = "true",
        allowedHeaders = "*"
)
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    private ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file){
        VideoDto videoDto = new VideoDto();

        Video savedVideo  = uploadService.uploadVideo(videoDto, file);

        if(savedVideo != null){
            return ResponseEntity.ok(savedVideo.getId());
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Video not uploaded");
        }
    }
}
