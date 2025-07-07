package com.pard.gz.zigu.apply.repository;

import com.pard.gz.zigu.apply.entity.Apply;
import com.pard.gz.zigu.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplyRepo extends JpaRepository<Apply, Long> {

    List<Apply> findByPost(Post post);

}
