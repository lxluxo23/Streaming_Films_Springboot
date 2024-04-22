package com.lxdevelop.peliculas.controller;

import com.lxdevelop.peliculas.model.Video;
import com.lxdevelop.peliculas.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/videos")

public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping
    public ResponseEntity<List<Video>> listVideos() {
        List<Video> videos = videoService.listAllVideos();
        return ResponseEntity.ok(videos);
    }





}