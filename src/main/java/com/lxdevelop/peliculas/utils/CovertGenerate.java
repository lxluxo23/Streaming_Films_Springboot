package com.lxdevelop.peliculas.utils;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.lxdevelop.peliculas.model.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component

public class CovertGenerate {
    public static final File coversDir = new File("./covers");
    private final Logger log = LoggerFactory.getLogger(CovertGenerate.class);

    public String generateCoverFromVideo(Video video) {
        if (!coversDir.exists()) {
            boolean wasDirectoryMade = coversDir.mkdirs();
            if (!wasDirectoryMade) {
                log.error("Failed to create directory: {}", coversDir);
                return null;
            }
        }
        String outputFileName = video.getId() + ".jpg";
        String outputPath = new File(coversDir, outputFileName).getPath();

        FFmpeg ffmpeg = FFmpeg.atPath()
                .addInput(UrlInput.fromUrl(video.getPath()))
                .addArguments("-ss", "00:00:01")
                .addArguments("-vframes", "1")
                .addOutput(UrlOutput.toUrl(outputPath))
                        .setOverwriteOutput(true);

        ffmpeg.execute();
        File outputFile = new File(outputPath);
        if (outputFile.exists()) {
            return outputPath;
        } else {
            log.error("Failed to create cover image: {}", outputPath);
            return null;
        }
    }

}