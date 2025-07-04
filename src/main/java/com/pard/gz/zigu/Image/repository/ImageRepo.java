package com.pard.gz.zigu.Image.repository;

import com.pard.gz.zigu.Image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
