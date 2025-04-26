package com.ShowTime.ShowTimeApp.controller;

import com.ShowTime.ShowTimeApp.modal.VideoDto;
import com.ShowTime.ShowTimeApp.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:3000")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    private ResponseEntity<String> uploadVideo(@RequestBody VideoDto videoDto){
        uploadService.uploadVideo(videoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
