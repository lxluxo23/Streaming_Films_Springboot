package com.lxdevelop.peliculas.controller;

import com.lxdevelop.peliculas.model.Video;
import com.lxdevelop.peliculas.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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