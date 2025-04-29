package com.ShowTime.ShowTimeApp.controller;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.service.VideoStreamService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/stream")
public class StreamingController {

    public static final int CHUNK_SIZE=1024*1024;   //1MB

    private final VideoStreamService videoStreamService;

    public StreamingController(VideoStreamService videoStreamService){
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/video/{id}")
    private ResponseEntity<Resource> streamContent(@PathVariable String id){
        Video video = videoStreamService.getVideo(id);

        String contentType = video.getContentType();
        if(null == contentType){
            contentType = "application/octet-stream";
        }
        String filePath = video.getFilePath();

        Resource resource = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/video/range/{id}")
    public ResponseEntity<Resource> streamVideoInRange(@PathVariable String videoId, @RequestHeader(value = "range", required = false) String range){
        Video video = videoStreamService.getVideo(videoId);

        Path path = Paths.get(video.getFilePath());

        Resource resource = new FileSystemResource(path);

        String contentType = video.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";

        }

        if (null == range) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        }

        long fileLength = path.toFile().length();
        long rangeStart;
        long rangeEnd;

        String[] ranges = range.replace("bytes=", "").split("-");
        rangeStart = Long.parseLong(ranges[0]);

        rangeEnd = rangeStart + CHUNK_SIZE - 1;
        if (rangeEnd >= fileLength) {
            rangeEnd = fileLength - 1;
        }

        try {
            InputStream inputStream = Files.newInputStream(path);
            inputStream.skip(rangeStart);
            long contentLength = rangeEnd - rangeStart + 1;

            byte[] data = new byte[(int) contentLength];

            int read = inputStream.read(data, 0, data.length);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("X-Content-Type-Options", "nosniff");
            headers.setContentLength(contentLength);

            return ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new ByteArrayResource(data));
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
