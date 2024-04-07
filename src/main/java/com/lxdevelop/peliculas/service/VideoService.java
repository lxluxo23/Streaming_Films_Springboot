package com.lxdevelop.peliculas.service;

import com.lxdevelop.peliculas.model.Video;
import com.lxdevelop.peliculas.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    VideoRepository videoRepository;

    public List<Video> listAllVideos() {
        return videoRepository.findAll();

    }
    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }



}