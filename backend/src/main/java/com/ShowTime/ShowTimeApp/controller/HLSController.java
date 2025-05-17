package com.ShowTime.ShowTimeApp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/hls/video")
public class HLSController {

    @Value("${files.video.hls}")
    private String HLS_DIR;

    @GetMapping("/{videoId}/master.m3u8")
    private ResponseEntity<Resource> serveMasterPlaylist(@PathVariable String videoId){
        if(videoId.equals("undefined")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Path path = Paths.get(HLS_DIR, videoId, "master.m3u8");
        if(!Files.exists(path)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource = new FileSystemResource(path);
        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_TYPE, "application/vnd.apple.mpegurl"
                )
                .body(resource);
    }

    // requested by video player parsing the .m3u8 playlist
    @GetMapping("/{videoId}/{segment}.ts")
    private ResponseEntity<?> serveSegments(@PathVariable String videoId, @PathVariable String segment){
        Path segmentPath = Paths.get(HLS_DIR, videoId, segment + ".ts");
        if (!Files.exists(segmentPath)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource = new FileSystemResource(segmentPath);
        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_TYPE, "video/mp2t"
                )
                .body(resource);
    }
}
