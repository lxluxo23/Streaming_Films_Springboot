package com.lxdevelop.peliculas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxdevelop.peliculas.utils.ConfigReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
@Component
public class MovieCoverService {
    private final Logger log = LoggerFactory.getLogger(MovieCoverService.class);
    private static final String API_KEY = new ConfigReader().getApiKey();
    private static final String BASE_URL = "https://api.themoviedb.org/3/search/movie?";

    public String fetchMovieCover(String movieName) {

        log.info("Obteniendo la portada de la película para: {}", movieName);
        String url = BASE_URL + "&query=" + URLEncoder.encode(movieName, StandardCharsets.UTF_8) + "&include_adult=true&language=es&page=1";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);
                log.info("Respuesta de la API: {}", rootNode);
                JsonNode resultsNode = rootNode.path("results");
                if (resultsNode.isArray() && !resultsNode.isEmpty()) {
                    JsonNode firstMovie = resultsNode.get(0);
                    String posterPath = firstMovie.path("poster_path").asText();
                    log.info("Portada de la película obtenida: {}", posterPath);
                    return "https://image.tmdb.org/t/p/w500" + posterPath;
                }
            }
        } catch (IOException e) {
            log.error("Error fetching movie cover: {}", e.getMessage());
            return null;
        }
        return null;
    }
}