package com.lxdevelop.peliculas.repository;

import com.lxdevelop.peliculas.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    boolean existsByNameAndPath(String name, String path);

}