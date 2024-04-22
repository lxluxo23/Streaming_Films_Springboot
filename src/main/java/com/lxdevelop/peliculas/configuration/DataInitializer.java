package com.lxdevelop.peliculas.configuration;

import com.lxdevelop.peliculas.enums.VideoFormat;
import com.lxdevelop.peliculas.model.Video;
import com.lxdevelop.peliculas.repository.VideoRepository;
import com.lxdevelop.peliculas.service.MovieCoverService;
import com.lxdevelop.peliculas.utils.CovertGenerate;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Component
@Profile("dev")

public class DataInitializer {

    @Autowired
    VideoRepository videoRepository;
    @Autowired
    MovieCoverService movieCoverService;
    @Autowired
    CovertGenerate covertGenerate;
    public static final File mediaDir = new File("./media");

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Scheduled(fixedRate = 30000)
    public void verificarYActualizarBaseDeDatos() {
        log.info("Verificando y actualizando base de datos");
        init();
        eliminarVideosNoEncontrados();

    }

    @PostConstruct
    public void init() {
        if (!mediaDir.exists()) {
            boolean dirCreated = mediaDir.mkdir();
            if (!dirCreated) {
                log.error("No se pudo crear el directorio: {}", mediaDir.getAbsolutePath());
                return;
            }
        }
        if (mediaDir.isDirectory()) {
            File[] files = mediaDir.listFiles((dir, name) -> VideoFormat.isSupported(name));
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    String filePath = Paths.get(file.getAbsolutePath()).normalize().toString();
                    if (!videoRepository.existsByNameAndPath(fileName, filePath)) {
                        Video video = new Video();
                        video.setName(fileName);
                        video.setPath(filePath);
                        videoRepository.save(video);
                    }
                }
            }
        }
    }

    @PostConstruct
    public void eliminarVideosNoEncontrados() {
        File[] files = mediaDir.listFiles((dir, name) -> VideoFormat.isSupported(name));
        if (files == null) return;
        Set<String> nombresArchivos = Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toSet());
        List<Video> todosLosVideos = videoRepository.findAll();
        List<Video> videosAEliminar = todosLosVideos.stream()
                .filter(video -> !nombresArchivos.contains(video.getName()))
                .toList();
        videoRepository.deleteAll(videosAEliminar);
    }


    @Scheduled(cron = "0 * * * * ?")
    public void fetchMovieCovers() {
        log.info(" [ Generando covers ] ");
        List<Video> videos = videoRepository.findAll();
        for (Video video : videos) {
            String movieName = video.getName().replace(".mp4", "");
            String coverUrl = movieCoverService.fetchMovieCover(movieName);
            if (coverUrl != null) {
                video.setImg(coverUrl);
                videoRepository.save(video);
            } else {
                log.error("No se pudo obtener la portada de la película: {}", movieName);
                log.info("Generando portada desde el video: {}", movieName);
                String coverImagePath = covertGenerate.generateCoverFromVideo(video);
                if (coverImagePath != null) {
                    log.info("Portada generada en: {}", coverImagePath);
                    video.setImg(coverImagePath);
                    videoRepository.save(video);
                } else {
                    log.error("No se pudo generar la portada de la película: {}", movieName);
                }
            }
        }
    }
}