package com.ShowTime.ShowTimeApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream")
@CrossOrigin("${allowCrossOrigin}")
public class StreamingController {

    @GetMapping("/video/{id}")
    private ResponseEntity<String> streamContent(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }
}
