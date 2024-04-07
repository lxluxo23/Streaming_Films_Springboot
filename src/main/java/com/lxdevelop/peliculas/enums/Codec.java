package com.lxdevelop.peliculas.enums;

import lombok.Getter;

@Getter
public enum Codec {
    AMD("h264_amf"),
    NVIDIA("h264_nvenc"),
    CPU("libx264");
    private final String ffmpegArgument;

    Codec(String ffmpegArgument) {
        this.ffmpegArgument = ffmpegArgument;
    }

    public static Codec fromString(String codecName) {
        for (Codec codec : Codec.values()) {
            if (codec.name().equalsIgnoreCase(codecName)) {
                return codec;
            }
        }
        return CPU;
    }
}