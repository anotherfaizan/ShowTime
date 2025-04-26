package com.ShowTime.ShowTimeApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("${allowCrossOrigin}")
public class UserController {

    @GetMapping("/profile")
    private ResponseEntity<String> getProfile(){
        return null;
    }

    @GetMapping("/search")
    private ResponseEntity<String> getDetails(){
        return null;
    }
}
