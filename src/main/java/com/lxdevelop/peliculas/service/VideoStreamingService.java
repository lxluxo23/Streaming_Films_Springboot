package com.lxdevelop.peliculas.service;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.PipeOutput;
import org.springframework.stereotype.Service;
import java.io.OutputStream;

@Service
public class VideoStreamingService {

    public void streamVideo(String videoPath, OutputStream outputStream) {
        FFmpeg.atPath()
                .addArguments("-i", videoPath)
                .addArguments("-vcodec", "h264_amf")
                .addArguments("-profile:v", "high")
                .addArguments("-b:v", "5000k")
                .addArguments("-maxrate", "5000k")
                .addArguments("-bufsize", "10000k")
                .addArguments("-c:a", "aac")
                .addArguments("-b:a", "320k")
                .addArguments("-f", "flv")
                .addOutput(PipeOutput.pumpTo(outputStream)
                        .setFormat("flv"))
                .addArgument("-nostdin")
                .execute();
    }
}