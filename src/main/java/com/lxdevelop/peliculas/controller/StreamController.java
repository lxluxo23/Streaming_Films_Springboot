package com.lxdevelop.peliculas.controller;

import com.lxdevelop.peliculas.enums.Codec;
import com.lxdevelop.peliculas.model.Video;
import com.lxdevelop.peliculas.service.VideoService;
import com.lxdevelop.peliculas.service.VideoStreamingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@Log4j2
public class StreamController {

    @Autowired
    VideoStreamingService videoStreamingService;

    @Autowired
    VideoService videoService;


    @GetMapping("/stream/{videoId}")
    public ResponseEntity<StreamingResponseBody> livestream(@PathVariable Long videoId, @RequestParam(required = false) String codec) {
        Video video = videoService.findVideoById(videoId);
        if (video != null) {
            Codec codecEnum = Codec.fromString(codec);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputStream -> videoStreamingService.streamVideo(video.getPath(), outputStream, codecEnum));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stream2/{videoId}")
    public ResponseEntity<UrlResource> getFullVideo(@PathVariable Long videoId) throws MalformedURLException {
        Video video = videoService.findVideoById(videoId);
        UrlResource videoResource = new UrlResource("file:" + video.getPath());
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(videoResource);
    }
    @GetMapping("/stream3/{videoId}")
    public Mono<Resource> getMonoResource(@PathVariable Long videoId) throws MalformedURLException {
        Video video = videoService.findVideoById(videoId);
        return Mono.just(new UrlResource("file:" + video.getPath()));
    }
}