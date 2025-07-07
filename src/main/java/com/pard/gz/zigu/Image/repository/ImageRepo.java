package com.pard.gz.zigu.Image.repository;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByPost(Post post);
}
