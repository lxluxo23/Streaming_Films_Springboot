package com.lxdevelop.peliculas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;
    private Path configPath;

    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);

    public ConfigReader() {
        this.properties = new Properties();
        this.configPath = Paths.get("config.properties");
        try {
            if (!Files.exists(configPath)) {
                createConfigFile();
            }
            try (InputStream input = Files.newInputStream(configPath)) {
                properties.load(input);
            }
        } catch (IOException ex) {
            log.error("Error al leer el archivo de configuración: {}", ex.getMessage());
        }
    }
    private void createConfigFile() throws IOException {
        try (OutputStream output = Files.newOutputStream(configPath)) {
            properties.setProperty("TMDB_API_KEY", "your_api_key");
            properties.store(output, null);
        }
    }
    public String getApiKey() {
        return this.properties.getProperty("TMDB_API_KEY");
    }
}