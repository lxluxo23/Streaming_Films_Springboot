package com.lxdevelop.peliculas.service;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.PipeOutput;
import com.lxdevelop.peliculas.enums.Codec;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class VideoStreamingService {

    public void streamVideo(String videoPath, OutputStream outputStream, Codec codec) {

        FFmpeg ffmpeg = FFmpeg.atPath()
                .addArguments("-i", videoPath)
                .addArguments("-b:v", "5000k")
                .addArguments("-maxrate", "5000k")
                .addArguments("-bufsize", "10000k")
                .addArguments("-c:a", "aac")
                .addArguments("-b:a", "320k")
                .addOutput(PipeOutput.pumpTo(outputStream)
                        .setFormat("flv"))
                .addArgument("-nostdin");

        if (codec == Codec.AMD) {
            ffmpeg.addArguments("-profile:v", "high");
        }
        ffmpeg.addArguments("-c:v", codec.getFfmpegArgument());
        ffmpeg.execute();
    }
}