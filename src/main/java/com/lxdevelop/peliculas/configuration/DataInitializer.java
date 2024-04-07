package com.lxdevelop.peliculas.configuration;

import com.lxdevelop.peliculas.model.Video;
import com.lxdevelop.peliculas.repository.VideoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Configuration
@Component
@Profile("dev")
public class DataInitializer {

    @Autowired
    VideoRepository videoRepository;

    @PostConstruct
    public void init() {
        File mediaDir = new File("./media");
        if (mediaDir.exists() && mediaDir.isDirectory()) {
            File[] files = mediaDir.listFiles((dir, name) -> name.endsWith(".mp4") || name.endsWith(".mkv") || name.endsWith(".flv"));
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    String filePath = Paths.get(file.getAbsolutePath()).normalize().toString();
                    Video video = new Video();
                    video.setName(fileName);
                    video.setPath(filePath);
                    videoRepository.save(video);
                }
            }
        }
    }
}