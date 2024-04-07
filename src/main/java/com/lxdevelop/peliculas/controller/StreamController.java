package com.lxdevelop.peliculas.controller;

import com.lxdevelop.peliculas.model.Video;
import com.lxdevelop.peliculas.service.VideoService;
import com.lxdevelop.peliculas.service.VideoStreamingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api")
@Log4j2
public class StreamController {

    @Autowired
    VideoStreamingService videoStreamingService;

    @Autowired
    VideoService videoService;


    @GetMapping("/stream/{videoId}")
    public ResponseEntity<StreamingResponseBody> livestream(@PathVariable Long videoId) {
        Video video = videoService.findVideoById(videoId);
        if (video != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputStream -> videoStreamingService.streamVideo(video.getPath(), outputStream));
        }
        return ResponseEntity.notFound().build();
    }
}