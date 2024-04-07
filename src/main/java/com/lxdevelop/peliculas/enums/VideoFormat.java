package com.lxdevelop.peliculas.enums;
import lombok.Getter;


@Getter
public enum VideoFormat {

    MP4(".mp4"),
    MKV(".mkv"),
    FLV(".flv");
    private final String extension;

    VideoFormat(String extension) {
        this.extension = extension;
    }

    public static boolean isSupported(String fileName) {
        for (VideoFormat format : values()) {
            if (fileName.endsWith(format.getExtension())) {
                return true;
            }
        }
        return false;
    }

}